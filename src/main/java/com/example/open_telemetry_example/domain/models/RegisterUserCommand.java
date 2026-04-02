package com.example.open_telemetry_example.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RegisterUserCommand {

    private String username;
    private String password;

}
