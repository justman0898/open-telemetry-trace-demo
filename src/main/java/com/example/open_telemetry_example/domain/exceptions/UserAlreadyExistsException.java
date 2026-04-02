package com.example.open_telemetry_example.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserAlreadyExistsException extends Throwable {
    private final HttpStatus status =  HttpStatus.CONFLICT;


    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
