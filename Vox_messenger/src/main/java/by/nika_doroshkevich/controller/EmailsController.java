package by.nika_doroshkevich.controller;

import by.nika_doroshkevich.model.Email;
import by.nika_doroshkevich.model.EmailsThread;
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

    @MessageMapping("/send-email-private/{id}/{userId}")
    public void sendMessagePrivate(@DestinationVariable String id,
                                   Email email, @DestinationVariable String userId) {
        EmailsThread emailsThread = emailsThreadService.storeEmail(Integer.parseInt(id), email, Integer.parseInt(userId));
        email.setSendingDateTime(emailsThread.getSendingDateTime());
        email.setSenderUserId(Integer.parseInt(userId));
        simpMessagingTemplate.convertAndSendToUser(
                id,
                "/topic/emails",
                email);
    }
}
