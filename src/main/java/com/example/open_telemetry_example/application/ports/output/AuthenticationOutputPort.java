package com.example.open_telemetry_example.application.ports.output;

import com.example.open_telemetry_example.domain.exceptions.UserAlreadyExistsException;
import com.example.open_telemetry_example.domain.models.RegisterUserCommand;
import com.example.open_telemetry_example.domain.models.RegisterUserResult;

public interface AuthenticationOutputPort {
    RegisterUserResult save(RegisterUserCommand command) throws UserAlreadyExistsException;
}
