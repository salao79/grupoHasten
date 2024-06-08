package com.grupoHasten.pruebaSalahdin.model.dto.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}