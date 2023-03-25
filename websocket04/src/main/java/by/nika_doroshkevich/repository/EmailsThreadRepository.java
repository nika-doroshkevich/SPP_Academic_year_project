package by.nika_doroshkevich.repository;

import by.nika_doroshkevich.model.EmailsThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailsThreadRepository extends JpaRepository<EmailsThread, Integer> {

    List<EmailsThread> findByWebSocketConnectionId(Integer websocketId);
}
