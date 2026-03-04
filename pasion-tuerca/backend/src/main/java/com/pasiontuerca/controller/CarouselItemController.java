package com.pasiontuerca.controller;

import com.pasiontuerca.model.CarouselItem;
import com.pasiontuerca.service.CarouselItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carousel")
public class CarouselItemController {

    @Autowired
    private CarouselItemService carouselItemService;

    @GetMapping
    public ResponseEntity<List<CarouselItem>> getAllActiveCarouselItems() {
        List<CarouselItem> items = carouselItemService.getAllActiveCarouselItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CarouselItem>> getAllCarouselItemsForAdmin() {
        List<CarouselItem> items = carouselItemService.getAllCarouselItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarouselItem> getCarouselItemByIdForAdmin(@PathVariable Long id) {
        return carouselItemService.getCarouselItemById(id)
                .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarouselItem> createCarouselItem(@RequestBody CarouselItem carouselItem) {
        CarouselItem savedItem = carouselItemService.saveCarouselItem(carouselItem);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @PutMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarouselItem> updateCarouselItem(@PathVariable Long id, @RequestBody CarouselItem carouselItem) {
        return carouselItemService.getCarouselItemById(id)
                .map(existingItem -> {
                    existingItem.setImageUrl(carouselItem.getImageUrl());
                    existingItem.setWhatsappLink(carouselItem.getWhatsappLink());
                    existingItem.setItemOrder(carouselItem.getItemOrder());
                    existingItem.setActive(carouselItem.isActive());
                    CarouselItem updatedItem = carouselItemService.saveCarouselItem(existingItem);
                    return new ResponseEntity<>(updatedItem, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCarouselItem(@PathVariable Long id) {
        if (carouselItemService.getCarouselItemById(id).isPresent()) {
            carouselItemService.deleteCarouselItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
