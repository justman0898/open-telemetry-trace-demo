package com.example.open_telemetry_example.application.ports.input;

import com.example.open_telemetry_example.domain.exceptions.UserAlreadyExistsException;
import com.example.open_telemetry_example.domain.models.RegisterUserCommand;
import com.example.open_telemetry_example.domain.models.RegisterUserResult;

public interface AuthenticationUseCase {

    RegisterUserResult register(RegisterUserCommand command) throws UserAlreadyExistsException;
}
