package com.example.open_telemetry_example.domain.bean_config;

import com.example.open_telemetry_example.application.ports.input.AuthenticationUseCase;
import com.example.open_telemetry_example.application.ports.output.AuthenticationOutputPort;
import com.example.open_telemetry_example.domain.service.AuthenticationService;
import com.example.open_telemetry_example.infrastructure.adapter.output.persistence.adapter.AuthenticationOutputAdapter;
import com.example.open_telemetry_example.infrastructure.adapter.output.persistence.repositories.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AuthServiceConfig {
    private final AuthenticationOutputPort authenticationOutputPort;
    @Bean
    public AuthenticationUseCase authenticationUseCase() {
        AuthenticationService authenticationService =
                new AuthenticationService(authenticationOutputPort);
        return authenticationService;
    }
}
