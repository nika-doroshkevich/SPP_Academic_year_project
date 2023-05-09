package by.nika_doroshkevich.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class EmailsThreadDto {

    private String message;
    private String userId;
    private LocalDate sendingDate;
    private LocalTime sendingTime;
}
