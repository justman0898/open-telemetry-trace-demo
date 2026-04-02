package com.example.open_telemetry_example.domain.service;

import com.example.open_telemetry_example.application.ports.input.AuthenticationUseCase;
import com.example.open_telemetry_example.application.ports.output.AuthenticationOutputPort;
import com.example.open_telemetry_example.domain.exceptions.UserAlreadyExistsException;
import com.example.open_telemetry_example.domain.models.RegisterUserCommand;
import com.example.open_telemetry_example.domain.models.RegisterUserResult;
import io.micrometer.tracing.annotation.NewSpan;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZonedDateTime;

@Slf4j
public class AuthenticationService implements AuthenticationUseCase {
    private final AuthenticationOutputPort authenticationOutputPort;


    public AuthenticationService(AuthenticationOutputPort authenticationOutputPort) {
        this.authenticationOutputPort = authenticationOutputPort;
    }

    @Override
    @NewSpan("user-register")
    public RegisterUserResult register(RegisterUserCommand command)
            throws UserAlreadyExistsException {
        log.info("Initiating register user...");
        String value = "";
        RegisterUserResult registerUserResult =
                authenticationOutputPort.save(command);
        registerUserResult.setExpiresAt(Instant.now());
        registerUserResult.setTimestamp(ZonedDateTime.now());
        registerUserResult.setJwtToken(value);
        registerUserResult.setTokenType("Bearer");
        log.info("New Registered user {}", registerUserResult);
        return registerUserResult;
    }
}
