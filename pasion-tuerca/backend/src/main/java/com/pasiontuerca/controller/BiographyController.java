package com.pasiontuerca.controller;

import com.pasiontuerca.model.Biography;
import com.pasiontuerca.model.BiographyComment;
import com.pasiontuerca.service.BiographyCommentService;
import com.pasiontuerca.service.BiographyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/biography")
public class BiographyController {
    private final BiographyService biographyService;
    private final BiographyCommentService biographyCommentService;

    public BiographyController(BiographyService biographyService, BiographyCommentService biographyCommentService){
        this.biographyService = biographyService;
        this.biographyCommentService = biographyCommentService;
    }

    @GetMapping
    public ResponseEntity<List<Biography>> all(){ return ResponseEntity.ok(biographyService.all()); }

    @PutMapping
    public ResponseEntity<?> upsert(@RequestBody Biography b){ return ResponseEntity.ok(biographyService.save(b)); }

    @GetMapping("/{biographyId}/comments")
    public ResponseEntity<List<BiographyComment>> getCommentsByBiographyId(@PathVariable UUID biographyId) {
        return ResponseEntity.ok(biographyCommentService.getCommentsByBiographyId(biographyId));
    }

    @PostMapping("/{biographyId}/comments")
    public ResponseEntity<BiographyComment> addCommentToBiography(
            @PathVariable UUID biographyId,
            @RequestBody BiographyComment comment) {
        BiographyComment newComment = biographyCommentService.addComment(biographyId, comment);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }
}
