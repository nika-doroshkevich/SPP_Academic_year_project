package by.nika_doroshkevich.model;

import lombok.*;

import javax.persistence.*;

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
}
