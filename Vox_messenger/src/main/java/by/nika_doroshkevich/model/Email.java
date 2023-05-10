package by.nika_doroshkevich.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    private String subject;
    private LocalTime sendingTime;
    private Integer senderUserId;
}
