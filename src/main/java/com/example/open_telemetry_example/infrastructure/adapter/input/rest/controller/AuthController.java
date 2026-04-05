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

    @PostMapping("/register-user")
    public ResponseEntity<Object> performRegistration(
            @RequestBody @Valid RegisterUserRequest request
    ) throws UserAlreadyExistsException {
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
    }


}
