package com.pasiontuerca.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "biography")
@Data
public class Biography {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(columnDefinition = "text")
    private String content;

    @ElementCollection
    @CollectionTable(name = "biography_image_urls", joinColumns = @JoinColumn(name = "biography_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    @ElementCollection
    @CollectionTable(name = "biography_video_urls", joinColumns = @JoinColumn(name = "biography_id"))
    @Column(name = "video_url")
    private List<String> videoUrls;

    @OneToMany(mappedBy = "biography", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<BiographyComment> comments;
}