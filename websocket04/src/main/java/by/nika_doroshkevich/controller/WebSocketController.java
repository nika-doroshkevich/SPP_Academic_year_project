package by.nika_doroshkevich.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    @GetMapping("/api/websockets/{first}/{second}")
    public String getOrCreateWebSocketConnection(@PathVariable String first,
                                                 @PathVariable String second){
        Integer webSocketId = 666;
        return String.valueOf(webSocketId);
    }
}
