package com.example.open_telemetry_example.domain.models.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Builder
@Getter
@Setter
public class UserRegisteredNotification {

    private String email;
    private Instant createdAt;
}
