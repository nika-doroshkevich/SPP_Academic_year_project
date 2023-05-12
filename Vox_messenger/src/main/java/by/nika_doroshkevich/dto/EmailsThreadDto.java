package by.nika_doroshkevich.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmailsThreadDto {

    private String message;
    private String userId;
    private LocalDateTime sendingDateTime;
}
