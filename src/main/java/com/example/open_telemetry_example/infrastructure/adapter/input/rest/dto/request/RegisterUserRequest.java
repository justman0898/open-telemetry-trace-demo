package com.example.open_telemetry_example.infrastructure.adapter.input.rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequest {
    @Email(message = "Invalid email")
    private String username;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}$")
    private String password;

}
