package tr.org.ji.pokedex.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tr.org.ji.pokedex.model.NotificationDTO;

@RestController
public class WebSocketNotificationController {
    @Autowired
    SimpMessagingTemplate template;

    @PostMapping("/send")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> sendMessage(@RequestBody NotificationDTO notificationDTO) {
        template.convertAndSend("/topic/message", notificationDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload NotificationDTO notificationDTO) {
        // receive message from client
    }

    @SendTo("/topic/message")
    public NotificationDTO broadcastMessage(@Payload NotificationDTO notificationDTO) {
        return notificationDTO;
    }
}
