package com.example.open_telemetry_example.infrastructure.adapter.output.persistence.mappers;

import com.example.open_telemetry_example.domain.models.RegisterUserCommand;
import com.example.open_telemetry_example.domain.models.RegisterUserResult;
import com.example.open_telemetry_example.infrastructure.adapter.output.persistence.entities.UserDetailsJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserPersistenceMapper {
    UserDetailsJpaEntity toUserDetailsJpaEntity(RegisterUserCommand command);

    RegisterUserResult toRegisterUserResult(UserDetailsJpaEntity entity);
}
