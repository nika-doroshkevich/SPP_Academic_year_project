package by.nika_doroshkevich.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    private String subject;
    private LocalDateTime sendingDateTime;
    private Integer senderUserId;
}
