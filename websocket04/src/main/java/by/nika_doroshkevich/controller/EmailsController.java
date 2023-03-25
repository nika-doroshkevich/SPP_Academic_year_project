package by.nika_doroshkevich.controller;

import by.nika_doroshkevich.model.Email;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class EmailsController {

    @MessageMapping("/send-email")
    @SendTo("/topic/emails")
    public Email sendMessage(Email email) {
        return email;
    }
}
