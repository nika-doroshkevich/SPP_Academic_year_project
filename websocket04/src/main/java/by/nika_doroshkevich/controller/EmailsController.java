package by.nika_doroshkevich.controller;

import by.nika_doroshkevich.model.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class EmailsController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/send-email")
    @SendTo("/topic/emails")
    public Email sendMessage(Email email) {
        return email;
    }

//    @MessageMapping("/send-email-private")
//    @SendTo("/topic/emails")
//    public Email sendMessagePrivate(Email email) {
//        return email;
//    }

    @MessageMapping("/send-email-private/{id}")
    public void sendMessagePrivate(@DestinationVariable String id, Email email) {
        simpMessagingTemplate.convertAndSendToUser(
                id,
                "/topic/emails",
                email);
    }
}
