package com.example.open_telemetry_example.domain.service;

import com.example.open_telemetry_example.application.ports.input.AuthenticationUseCase;
import com.example.open_telemetry_example.application.ports.output.AuthenticationOutputPort;
import com.example.open_telemetry_example.application.ports.output.UserRegistrationOutputPort;
import com.example.open_telemetry_example.domain.exceptions.UserAlreadyExistsException;
import com.example.open_telemetry_example.domain.models.RegisterUserCommand;
import com.example.open_telemetry_example.domain.models.RegisterUserResult;
import com.example.open_telemetry_example.domain.validation.LogSanitizer;
import io.micrometer.tracing.annotation.NewSpan;
import io.micrometer.tracing.annotation.SpanTag;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZonedDateTime;

@Slf4j
public class AuthenticationService implements AuthenticationUseCase {
    private final AuthenticationOutputPort authenticationOutputPort;
    private final UserRegistrationOutputPort userRegistrationOutputPort;
    private final Tracer tracer;


    public AuthenticationService(
            AuthenticationOutputPort authenticationOutputPort,
            UserRegistrationOutputPort userRegistrationOutputPort,
            Tracer tracer)
    {
        this.authenticationOutputPort = authenticationOutputPort;
        this.userRegistrationOutputPort = userRegistrationOutputPort;
        this.tracer = tracer;
    }

    @Override
//    @NewSpan("user-register")
    public RegisterUserResult register(RegisterUserCommand command)
            throws UserAlreadyExistsException {
        Span span = tracer.spanBuilder("Authentication Service").startSpan();

        log.info("Initiating register user in authentication service...");

        try(Scope ignored = span.makeCurrent()) {
            Span.current().setAttribute("user.username", command.getUsername());
            log.info("Current context(Authservice): {}", Context.current());
            String value = "";
            RegisterUserResult registerUserResult =
                    authenticationOutputPort.save(command);
            registerUserResult.setExpiresAt(Instant.now());
            registerUserResult.setTimestamp(ZonedDateTime.now());
            registerUserResult.setJwtToken(value);
            registerUserResult.setTokenType("Bearer");
            log.info("New Registered user {}", registerUserResult.getId());
            userRegistrationOutputPort.sendUserRegistrationNotification(command);

            span.setAttribute("user.id", registerUserResult.getId());// add id as properties in span
            return registerUserResult;
        }finally {
            span.end();
        }
    }
}
