package com.pasiontuerca.controller;

import com.pasiontuerca.model.Media;
import com.pasiontuerca.service.MediaService;
import com.pasiontuerca.storage.StorageService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List; // Import List

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

    private final MediaService service;

    @Value("${pasion.uploads-dir:uploads}")
    private String uploadsDir;

    private final StorageService storage;

    public MediaController(MediaService service, @Qualifier("localStorageService") StorageService storage) {
        this.service = service;
        this.storage = storage;
    }

    @GetMapping
    public ResponseEntity<List<Media>> getAllMedia() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/carousel")
    public ResponseEntity<java.util.List<Media>> carousel(){
        return ResponseEntity.ok(service.activeCarousel());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@RequestParam MultipartFile file,
                                    @RequestParam(required=false) String title,
                                    @RequestParam(required=false) String type,
                                    @RequestParam(required=false) String whatsapp) throws IOException {
        if (file.isEmpty()) return ResponseEntity.badRequest().body("Empty file");
        String original = StringUtils.cleanPath(file.getOriginalFilename());
        int i = original.lastIndexOf('.');
        if (i>=0) {
        }
        String url = storage.store(file);
        Media m = new Media();
        m.setTitle(title!=null?title:original);
        m.setType(type!=null?type:"IMAGE");
        m.setUrl(url);
        m.setWhatsappContact(whatsapp);
        Media saved = service.save(m);
        return ResponseEntity.ok(saved);
    }
}