package com.grupoHasten.pruebaSalahdin.exception;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDetails {
    private Date timestamp;
    private String message;
    private String details;

}
