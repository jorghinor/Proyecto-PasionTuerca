package com.pasiontuerca.repository;

import com.pasiontuerca.model.CarouselItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarouselItemRepository extends JpaRepository<CarouselItem, Long> {
    List<CarouselItem> findByActiveTrueOrderByItemOrderAsc();
}
