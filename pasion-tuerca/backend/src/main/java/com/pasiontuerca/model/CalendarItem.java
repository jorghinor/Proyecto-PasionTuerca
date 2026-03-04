package com.pasiontuerca.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "calendar_item")
@Data
public class CalendarItem {
    @Id
    @GeneratedValue
    private UUID id;
    private LocalDate date;
    private String title;
    @Column(columnDefinition = "text")
    private String comment;
    @ManyToOne
    private Media media;
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
