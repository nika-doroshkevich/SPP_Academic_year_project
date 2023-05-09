package by.nika_doroshkevich.controller;

import by.nika_doroshkevich.service.WebSocketConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebSocketController {

    private final WebSocketConnectionService webSocketConnectionService;

    @GetMapping("/api/websockets/{first}/{second}")
    public String getOrCreateWebSocketConnection(@PathVariable String first,
                                                 @PathVariable String second) {
        return webSocketConnectionService.getWebSocketConnectionIdAsString(first, second);
    }
}
