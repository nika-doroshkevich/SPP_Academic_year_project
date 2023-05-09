package by.nika_doroshkevich.service.impl;

import by.nika_doroshkevich.model.Email;
import by.nika_doroshkevich.model.EmailsThread;
import by.nika_doroshkevich.model.WebSocketConnection;
import by.nika_doroshkevich.repository.EmailsThreadRepository;
import by.nika_doroshkevich.repository.WebSocketConnectionRepository;
import by.nika_doroshkevich.service.EmailsThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailsThreadServiceImpl implements EmailsThreadService {

    private final EmailsThreadRepository emailsThreadRepository;
    private final WebSocketConnectionRepository webSocketConnectionRepository;

    @Override
    public List<EmailsThread> getEmailsBySocketId(String socketId) {
        Integer id = Integer.parseInt(socketId);
        List<EmailsThread> emailsThreads = emailsThreadRepository.findByWebSocketConnectionId(id);
        return emailsThreads;
    }

    @Override
    public EmailsThread storeEmail(Integer socketId, Email email, Integer userId) {
        WebSocketConnection webSocketConnection = webSocketConnectionRepository.findById(socketId).orElse(null);
        EmailsThread newEmailsThread = EmailsThread.builder()
                .webSocketConnection(webSocketConnection)
                .emailSubject(email.getSubject())
                .userId(userId)
                .sendingDate(LocalDate.now())
                .sendingTime(LocalTime.now())
                .build();
        EmailsThread savedEmailsThread = emailsThreadRepository.save(newEmailsThread);
        return savedEmailsThread;
    }
}
