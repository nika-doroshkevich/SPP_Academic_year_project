package by.nika_doroshkevich.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "web_socket_connection")
public class WebSocketConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_user")
    private String firstUser;

    @Column(name = "second_user")
    private String secondUser;

    @OneToMany(mappedBy = "webSocketConnection")
    private Set<EmailsThread> emailsThreads;
}
