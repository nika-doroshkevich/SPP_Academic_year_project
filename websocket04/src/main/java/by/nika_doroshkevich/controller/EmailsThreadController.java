package by.nika_doroshkevich.controller;

import by.nika_doroshkevich.service.EmailsThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmailsThreadController {

    private final EmailsThreadService emailsThreadService;

//    @GetMapping(value = "/api/emails/{socketId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<EmailsThread> getOrCreateWebSocketConnection(@PathVariable String socketId) {
//        return emailsThreadService.getEmailsBySocketId(socketId);
//    }

    @GetMapping(value = "/api/emails/{socketId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getOrCreateWebSocketConnection(@PathVariable String socketId) {
        return Arrays.asList("hi", "hello");
    }
}
