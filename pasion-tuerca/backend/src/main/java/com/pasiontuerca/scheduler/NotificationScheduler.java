package com.pasiontuerca.scheduler;

import com.pasiontuerca.model.Notification;
import com.pasiontuerca.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class NotificationScheduler {
    private final NotificationService notificationService;
    private final Logger log = LoggerFactory.getLogger(NotificationScheduler.class);

    public NotificationScheduler(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @Scheduled(fixedRate = 60000) // every minute
    public void checkAndSend(){
        List<Notification> due = notificationService.dueNotifications(OffsetDateTime.now());
        for(Notification n : due){
            // Simulate sending: in prod integrate FCM/APNs
            log.info("[NotificationScheduler] Sending notification {} -> {}", n.getTitle(), n.getMessage());
            notificationService.markSent(n);
        }
    }
}
