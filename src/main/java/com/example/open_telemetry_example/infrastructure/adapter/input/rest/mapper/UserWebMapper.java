package com.example.open_telemetry_example.infrastructure.adapter.input.rest.mapper;

import com.example.open_telemetry_example.domain.models.RegisterUserCommand;
import com.example.open_telemetry_example.domain.models.RegisterUserResult;
import com.example.open_telemetry_example.infrastructure.adapter.input.rest.dto.request.RegisterUserRequest;
import com.example.open_telemetry_example.infrastructure.adapter.input.rest.dto.response.RegisterUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserWebMapper {
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    RegisterUserCommand toRegisterUserCommand(RegisterUserRequest request);

    RegisterUserResponse toRegisterUserResponse(RegisterUserResult registerUserResult);
}
