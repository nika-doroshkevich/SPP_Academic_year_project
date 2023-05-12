package by.nika_doroshkevich.controller;

import by.nika_doroshkevich.dto.EmailsThreadDto;
import by.nika_doroshkevich.model.EmailsThread;
import by.nika_doroshkevich.service.EmailsThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmailsThreadController {

    private final EmailsThreadService emailsThreadService;

    @GetMapping(value = "/api/emails/{socketId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmailsThreadDto> getOrCreateWebSocketConnection(@PathVariable String socketId) {
        List<EmailsThread> listEmailsThread = emailsThreadService.getEmailsBySocketId(socketId);
        List<EmailsThreadDto> listEmailsThreadDto = new ArrayList<>();
        for (EmailsThread email : listEmailsThread) {
            EmailsThreadDto emailDto = new EmailsThreadDto();
            emailDto.setMessage(email.getEmailSubject());
            emailDto.setUserId(email.getUserId().toString());
            emailDto.setSendingDateTime(email.getSendingDateTime());
            listEmailsThreadDto.add(emailDto);
        }
        return listEmailsThreadDto;
    }
}
