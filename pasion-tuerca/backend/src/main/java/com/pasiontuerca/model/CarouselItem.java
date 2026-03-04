package com.pasiontuerca.model;

import jakarta.persistence.*;

@Entity
@Table(name = "carousel_items")
public class CarouselItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String whatsappLink;

    @Column(nullable = false)
    private Integer itemOrder;

    @Column(nullable = false)
    private boolean active = true;

    // Constructors
    public CarouselItem() {
    }

    public CarouselItem(String imageUrl, String whatsappLink, Integer itemOrder, boolean active) {
        this.imageUrl = imageUrl;
        this.whatsappLink = whatsappLink;
        this.itemOrder = itemOrder;
        this.active = active;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWhatsappLink() {
        return whatsappLink;
    }

    public void setWhatsappLink(String whatsappLink) {
        this.whatsappLink = whatsappLink;
    }

    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
