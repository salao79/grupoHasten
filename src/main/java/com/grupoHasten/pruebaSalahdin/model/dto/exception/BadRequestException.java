package com.grupoHasten.pruebaSalahdin.model.dto.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}