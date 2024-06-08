package com.grupoHasten.pruebaSalahdin.configuration.exception;

import com.grupoHasten.pruebaSalahdin.model.dto.exception.BadRequestException;
import com.grupoHasten.pruebaSalahdin.model.dto.exception.ExceptionDetails;
import com.grupoHasten.pruebaSalahdin.model.dto.exception.ResourceNotFoundException;
import com.grupoHasten.pruebaSalahdin.model.dto.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class CentralizedExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        return this.createExceptionDetails(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDetails> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        return this.createExceptionDetails(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionDetails> handleUnauthorizedException(UnauthorizedException ex, HttpServletRequest request) {
        return this.createExceptionDetails(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDetails> handleException(Exception ex, HttpServletRequest request) {
        return this.createExceptionDetails(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    private ResponseEntity<ExceptionDetails> createExceptionDetails(HttpStatus status, String message, HttpServletRequest request) {
        ExceptionDetails errorResponse = new ExceptionDetails(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, status);
    }
}
