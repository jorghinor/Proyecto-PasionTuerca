package com.pasiontuerca.service;

import com.pasiontuerca.model.CalendarComment;
import com.pasiontuerca.model.CalendarEvent;
import com.pasiontuerca.repository.CalendarCommentRepository;
import com.pasiontuerca.repository.CalendarEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarService {

    @Autowired
    private CalendarEventRepository calendarEventRepository;

    @Autowired
    private CalendarCommentRepository calendarCommentRepository;

    // Calendar Event operations
    public List<CalendarEvent> getAllCalendarEvents() {
        return calendarEventRepository.findAll();
    }

    public Optional<CalendarEvent> getCalendarEventById(Long id) {
        return calendarEventRepository.findById(id);
    }

    public List<CalendarEvent> getCalendarEventsByDate(LocalDate date) {
        return calendarEventRepository.findByDateOrderByItemOrderAsc(date);
    }

    public CalendarEvent createCalendarEvent(CalendarEvent calendarEvent) {
        return calendarEventRepository.save(calendarEvent);
    }

    public CalendarEvent updateCalendarEvent(Long id, CalendarEvent updatedEvent) {
        return calendarEventRepository.findById(id).map(event -> {
            event.setDate(updatedEvent.getDate());
            event.setTitle(updatedEvent.getTitle());
            event.setDescription(updatedEvent.getDescription());
            event.setImageUrl(updatedEvent.getImageUrl());
            event.setVideoUrl(updatedEvent.getVideoUrl());
            event.setCommentBoxEnabled(updatedEvent.isCommentBoxEnabled());
            event.setMaxAssetsPerDay(updatedEvent.getMaxAssetsPerDay());
            event.setItemOrder(updatedEvent.getItemOrder());
            return calendarEventRepository.save(event);
        }).orElseThrow(() -> new RuntimeException("CalendarEvent not found with id " + id));
    }

    public void deleteCalendarEvent(Long id) {
        calendarEventRepository.deleteById(id);
    }

    // Calendar Comment operations
    public List<CalendarComment> getCommentsByEventId(Long eventId) {
        return calendarCommentRepository.findByCalendarEventIdOrderByTimestampAsc(eventId);
    }

    public CalendarComment addCommentToEvent(Long eventId, CalendarComment comment) {
        return calendarEventRepository.findById(eventId).map(event -> {
            comment.setCalendarEvent(event);
            comment.setTimestamp(LocalDateTime.now());
            return calendarCommentRepository.save(comment);
        }).orElseThrow(() -> new RuntimeException("CalendarEvent not found with id " + eventId));
    }

    public void deleteComment(Long commentId) {
        calendarCommentRepository.deleteById(commentId);
    }
}