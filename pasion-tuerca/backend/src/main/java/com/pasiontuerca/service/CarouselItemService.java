package com.pasiontuerca.service;

import com.pasiontuerca.model.CarouselItem;
import com.pasiontuerca.repository.CarouselItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarouselItemService {

    @Autowired
    private CarouselItemRepository carouselItemRepository;

    public List<CarouselItem> getAllActiveCarouselItems() {
        return carouselItemRepository.findByActiveTrueOrderByItemOrderAsc();
    }

    public List<CarouselItem> getAllCarouselItems() {
        return carouselItemRepository.findAll();
    }

    public Optional<CarouselItem> getCarouselItemById(Long id) {
        return carouselItemRepository.findById(id);
    }

    public CarouselItem saveCarouselItem(CarouselItem carouselItem) {
        return carouselItemRepository.save(carouselItem);
    }

    public void deleteCarouselItem(Long id) {
        carouselItemRepository.deleteById(id);
    }
}
