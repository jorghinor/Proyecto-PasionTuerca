package com.pasiontuerca.model;

import jakarta.persistence.*;
import java.time.LocalDate;
//import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "calendar_events")
public class CalendarEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String title;
    private String description;
    private String imageUrl;
    private String videoUrl;
    private boolean commentBoxEnabled;
    private int maxAssetsPerDay;
    private int itemOrder;

    @OneToMany(mappedBy = "calendarEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CalendarComment> comments;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public boolean isCommentBoxEnabled() {
        return commentBoxEnabled;
    }

    public void setCommentBoxEnabled(boolean commentBoxEnabled) {
        this.commentBoxEnabled = commentBoxEnabled;
    }

    public int getMaxAssetsPerDay() {
        return maxAssetsPerDay;
    }

    public void setMaxAssetsPerDay(int maxAssetsPerDay) {
        this.maxAssetsPerDay = maxAssetsPerDay;
    }

    public int getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(int itemOrder) {
        this.itemOrder = itemOrder;
    }

    public List<CalendarComment> getComments() {
        return comments;
    }

    public void setComments(List<CalendarComment> comments) {
        this.comments = comments;
    }
}