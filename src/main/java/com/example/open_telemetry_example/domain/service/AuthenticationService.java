package com.example.open_telemetry_example.domain.service;

import com.example.open_telemetry_example.application.ports.input.AuthenticationUseCase;
import com.example.open_telemetry_example.application.ports.output.AuthenticationOutputPort;
import com.example.open_telemetry_example.application.ports.output.UserRegistrationOutputPort;
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
    private final UserRegistrationOutputPort userRegistrationOutputPort;


    public AuthenticationService(
            AuthenticationOutputPort authenticationOutputPort,
            UserRegistrationOutputPort userRegistrationOutputPort)
    {
        this.authenticationOutputPort = authenticationOutputPort;
        this.userRegistrationOutputPort = userRegistrationOutputPort;
    }

    @Override
    @NewSpan("user-register")
    public RegisterUserResult register(RegisterUserCommand command)
            throws UserAlreadyExistsException {
        log.info("Initiating register user in authentication service...");
        String value = "";
        RegisterUserResult registerUserResult =
                authenticationOutputPort.save(command);
        registerUserResult.setExpiresAt(Instant.now());
        registerUserResult.setTimestamp(ZonedDateTime.now());
        registerUserResult.setJwtToken(value);
        registerUserResult.setTokenType("Bearer");
        log.info("New Registered user {}", registerUserResult.getId());
        userRegistrationOutputPort.sendUserRegistrationNotification(command);
        return registerUserResult;
    }
}
