package by.nika_doroshkevich.controller;

import by.nika_doroshkevich.model.Email;
import by.nika_doroshkevich.service.EmailsThreadService;
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
    private final EmailsThreadService emailsThreadService;

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
        emailsThreadService.storeEmail(Integer.parseInt(id), email);
        simpMessagingTemplate.convertAndSendToUser(
                id,
                "/topic/emails",
                email);
    }
}
