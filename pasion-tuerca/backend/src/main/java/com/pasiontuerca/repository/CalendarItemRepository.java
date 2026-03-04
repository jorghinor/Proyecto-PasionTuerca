package com.pasiontuerca.repository;
import com.pasiontuerca.model.CalendarItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CalendarItemRepository extends JpaRepository<CalendarItem, UUID> {
    List<CalendarItem> findByDate(LocalDate date);
}
