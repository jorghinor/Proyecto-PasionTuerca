package com.pasiontuerca.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "notification")
@Data
public class Notification {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    @Column(columnDefinition = "text")
    private String message;
    private OffsetDateTime scheduledAt;
    private boolean sent = false;
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
