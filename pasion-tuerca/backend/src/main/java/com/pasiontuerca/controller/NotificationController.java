package com.pasiontuerca.controller;

import com.pasiontuerca.model.Notification;
import com.pasiontuerca.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private final NotificationService service;
    public NotificationController(NotificationService service){ this.service = service; }

    @GetMapping
    public ResponseEntity<List<Notification>> all(){ return ResponseEntity.ok(service.dueNotifications(java.time.OffsetDateTime.now().plusYears(100))); }

    @PostMapping
    public ResponseEntity<?> schedule(@RequestBody Notification n){
        return ResponseEntity.ok(service.save(n));
    }
}
