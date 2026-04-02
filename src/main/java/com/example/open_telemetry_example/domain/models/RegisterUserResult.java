package com.example.open_telemetry_example.domain.models;

import lombok.*;

import java.time.Instant;
import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class RegisterUserResult {
    private String id;
    private String jwtToken;
    private String tokenType;
    private Instant expiresAt;
    private ZonedDateTime timestamp;
}
