package by.nika_doroshkevich.service.impl;

import by.nika_doroshkevich.model.WebSocketConnection;
import by.nika_doroshkevich.repository.WebSocketConnectionRepository;
import by.nika_doroshkevich.service.WebSocketConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketConnectionServiceImpl implements WebSocketConnectionService {

    private final WebSocketConnectionRepository webSocketConnectionRepository;

    @Override
    public String getWebSocketConnectionIdAsString(String first, String second) {
        WebSocketConnection webSocketConnection = webSocketConnectionRepository
                .getByConnectedUsers(first, second);
        Integer webSocketId = null;

        if (webSocketConnection == null) {
            WebSocketConnection newWebSocketConnection = WebSocketConnection.builder()
                    .firstUser(first)
                    .secondUser(second)
                    .build();
            WebSocketConnection savedWebSocketConnection = webSocketConnectionRepository.save(newWebSocketConnection);
            webSocketId = savedWebSocketConnection.getId();
        } else {
            webSocketId = webSocketConnection.getId();
        }

        return String.valueOf(webSocketId);
    }
}
