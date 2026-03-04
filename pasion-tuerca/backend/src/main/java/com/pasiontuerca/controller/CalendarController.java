package com.pasiontuerca.controller;

import com.pasiontuerca.model.CalendarComment;
import com.pasiontuerca.model.CalendarEvent;
import com.pasiontuerca.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    // Public endpoint to get events by date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<CalendarEvent>> getCalendarEventsByDate(@PathVariable("date") LocalDate date) {
        List<CalendarEvent> events = calendarService.getCalendarEventsByDate(date);
        return ResponseEntity.ok(events);
    }

    // Admin endpoints for Calendar Events
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<List<CalendarEvent>> getAllCalendarEvents() {
        List<CalendarEvent> events = calendarService.getAllCalendarEvents();
        return ResponseEntity.ok(events);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/{id}")
    public ResponseEntity<CalendarEvent> getCalendarEventById(@PathVariable Long id) {
        return calendarService.getCalendarEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<CalendarEvent> createCalendarEvent(@RequestBody CalendarEvent calendarEvent) {
        CalendarEvent createdEvent = calendarService.createCalendarEvent(calendarEvent);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/{id}")
    public ResponseEntity<CalendarEvent> updateCalendarEvent(@PathVariable Long id, @RequestBody CalendarEvent calendarEvent) {
        try {
            CalendarEvent updatedEvent = calendarService.updateCalendarEvent(id, calendarEvent);
            return ResponseEntity.ok(updatedEvent);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteCalendarEvent(@PathVariable Long id) {
        calendarService.deleteCalendarEvent(id);
        return ResponseEntity.noContent().build();
    }

    // Admin endpoints for Calendar Comments
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/{eventId}/comments")
    public ResponseEntity<List<CalendarComment>> getCommentsForEvent(@PathVariable Long eventId) {
        List<CalendarComment> comments = calendarService.getCommentsByEventId(eventId);
        return ResponseEntity.ok(comments);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/{eventId}/comments")
    public ResponseEntity<CalendarComment> addCommentToEvent(@PathVariable Long eventId, @RequestBody CalendarComment comment) {
        try {
            CalendarComment newComment = calendarService.addCommentToEvent(eventId, comment);
            return new ResponseEntity<>(newComment, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        calendarService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}