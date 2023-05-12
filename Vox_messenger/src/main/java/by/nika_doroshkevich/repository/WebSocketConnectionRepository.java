package by.nika_doroshkevich.repository;

import by.nika_doroshkevich.model.WebSocketConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WebSocketConnectionRepository extends JpaRepository<WebSocketConnection, Integer> {

    @Query(" select wsc from WebSocketConnection wsc " +
            " where (wsc.firstUser = :firstUser and wsc.secondUser = :secondUser) " +
            " or (wsc.firstUser = :secondUser and wsc.secondUser = :firstUser)")
    WebSocketConnection getByConnectedUsers(
            @Param("firstUser") String firstUser,
            @Param("secondUser") String secondUser);
}
