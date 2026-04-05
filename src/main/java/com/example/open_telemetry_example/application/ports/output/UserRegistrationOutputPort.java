package com.example.open_telemetry_example.application.ports.output;

import com.example.open_telemetry_example.domain.models.RegisterUserCommand;
import org.apache.catalina.User;

public interface UserRegistrationOutputPort {
    void sendUserRegistrationNotification(RegisterUserCommand user);
}
