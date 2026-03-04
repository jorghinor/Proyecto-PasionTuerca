package com.pasiontuerca.repository;
import com.pasiontuerca.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findBySentFalseAndScheduledAtBefore(OffsetDateTime when);
}
