package com.pasiontuerca.repository;

import com.pasiontuerca.model.CalendarComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarCommentRepository extends JpaRepository<CalendarComment, Long> {
    List<CalendarComment> findByCalendarEventIdOrderByTimestampAsc(Long calendarEventId);
}