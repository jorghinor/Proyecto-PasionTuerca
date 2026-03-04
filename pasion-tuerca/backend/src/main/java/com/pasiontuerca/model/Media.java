package com.pasiontuerca.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "media")
@Data
public class Media {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String type; // IMAGE | VIDEO | POSTER
    @Column(columnDefinition = "text")
    private String url;
    private String whatsappContact;
    private Integer orderIndex = 0;
    private Boolean active = true;
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
