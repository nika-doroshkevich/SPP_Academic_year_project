package by.nika_doroshkevich.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "emails_thread")
public class EmailsThread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "web_socket_connection_id", nullable = false)
    private WebSocketConnection webSocketConnection;

    @Column(name = "email_subject")
    private String emailSubject;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "sending_datetime")
    private LocalDateTime sendingDateTime;
}
