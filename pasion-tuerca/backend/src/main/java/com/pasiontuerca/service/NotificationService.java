package com.pasiontuerca.service;

import com.pasiontuerca.model.Notification;
import com.pasiontuerca.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
//import java.io.IOException;

@Service
public class NotificationService {
    private final NotificationRepository repo;

    @Value("${firebase.service-account:}")
    private String firebaseServiceAccountPath;

    private boolean firebaseInitialized = false;

    public NotificationService(NotificationRepository repo){
        this.repo = repo;
        initFirebase();
    }

    private void initFirebase(){
        try {
            if(firebaseServiceAccountPath!=null && !firebaseServiceAccountPath.isBlank()){
                FileInputStream serviceAccount = new FileInputStream(firebaseServiceAccountPath);
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
                FirebaseApp.initializeApp(options);
                firebaseInitialized = true;
            }
        } catch (Exception e){
            // ignore - firebase not configured
            firebaseInitialized = false;
        }
    }

    public Notification save(Notification n){ return repo.save(n); }
    public List<Notification> dueNotifications(OffsetDateTime when){ return repo.findBySentFalseAndScheduledAtBefore(when); }
    public void markSent(Notification n){ n.setSent(true); repo.save(n); }

    public void sendPush(String title, String message, String token){
        if(!firebaseInitialized) return;
        Message msg = Message.builder()
                .putData("title", title)
                .putData("body", message)
                .setToken(token)
                .build();
        try {
            String resp = FirebaseMessaging.getInstance().send(msg);
            System.out.println("Firebase send response: "+resp);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void delete(UUID id){ repo.deleteById(id); }
}
