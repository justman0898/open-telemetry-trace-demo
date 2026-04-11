package com.example.open_telemetry_example.infrastructure.adapter.input.rest.controller;

import com.example.open_telemetry_example.application.ports.input.AuthenticationUseCase;
import com.example.open_telemetry_example.domain.exceptions.UserAlreadyExistsException;
import com.example.open_telemetry_example.domain.messages.UrlConstant;
import com.example.open_telemetry_example.domain.models.RegisterUserCommand;
import com.example.open_telemetry_example.domain.models.RegisterUserResult;
import com.example.open_telemetry_example.domain.validation.LogSanitizer;
import com.example.open_telemetry_example.infrastructure.adapter.input.rest.dto.general.ApiResponse;
import com.example.open_telemetry_example.infrastructure.adapter.input.rest.dto.request.RegisterUserRequest;
import com.example.open_telemetry_example.infrastructure.adapter.input.rest.dto.response.RegisterUserResponse;
import com.example.open_telemetry_example.infrastructure.adapter.input.rest.mapper.UserWebMapper;
import io.micrometer.tracing.annotation.NewSpan;
//import io.micrometer.tracing.Tracer;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlConstant.BASE_URL + "/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthController {
    private final AuthenticationUseCase authenticationUseCase;
    private final UserWebMapper userWebMapper;
    private final Tracer tracer;

    @PostMapping("/register-user")
//    @NewSpan("user.register.controller")
    public ResponseEntity<Object> performRegistration(
            @RequestBody @Valid RegisterUserRequest request
    ) throws UserAlreadyExistsException {

        Span span = tracer.spanBuilder("user.register.controller").startSpan();

        try(Scope scope = span.makeCurrent()) {

            Span.current().setAttribute("username", request.getUsername());
//            Span.current().setAttribute("span_name", "user.register.controller");
            log.info("Context(Controller Span): {}", Context.current());
//        SpanBuilder span = tracer.spanBuilder("user.register");
            String safeEmail = LogSanitizer.sanitize(LogSanitizer.maskEmail(request.getUsername()));
            log.info("Initiating register user at controller for username:  {}", safeEmail);

            RegisterUserCommand registerUserCommand =
                    userWebMapper.toRegisterUserCommand(request);
            RegisterUserResult registerUserResult =
                    authenticationUseCase.register(registerUserCommand);

            RegisterUserResponse result = userWebMapper
                    .toRegisterUserResponse(registerUserResult);
            log.info("Successfully registered user {}", safeEmail);


            return ResponseEntity.ok(
                    ApiResponse.ok(result)
            );
        }finally {
            span.end();
        }
    }


}
