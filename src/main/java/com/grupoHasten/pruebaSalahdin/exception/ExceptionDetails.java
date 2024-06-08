package com.grupoHasten.pruebaSalahdin.exception;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDetails {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ExceptionDetails(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }



}
