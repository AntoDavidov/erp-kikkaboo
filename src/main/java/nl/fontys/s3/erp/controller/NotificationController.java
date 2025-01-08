package nl.fontys.s3.erp.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.domain.announcements.Announcement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("notifications")
public class NotificationController {
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ResponseEntity<Void> createNotification(@RequestBody Announcement announcement) {
        System.out.println("Received message: " + announcement);
        messagingTemplate.convertAndSend("/topic/notifications", announcement);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
