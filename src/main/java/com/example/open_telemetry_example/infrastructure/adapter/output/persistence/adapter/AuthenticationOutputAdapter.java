package com.example.open_telemetry_example.infrastructure.adapter.output.persistence.adapter;

import com.example.open_telemetry_example.application.ports.output.AuthenticationOutputPort;
import com.example.open_telemetry_example.domain.exceptions.UserAlreadyExistsException;
import com.example.open_telemetry_example.domain.models.RegisterUserCommand;
import com.example.open_telemetry_example.domain.models.RegisterUserResult;
import com.example.open_telemetry_example.infrastructure.adapter.output.persistence.entities.UserDetailsJpaEntity;
import com.example.open_telemetry_example.infrastructure.adapter.output.persistence.mappers.UserPersistenceMapper;
import com.example.open_telemetry_example.infrastructure.adapter.output.persistence.repositories.JpaUserRepository;
import io.micrometer.tracing.annotation.NewSpan;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationOutputAdapter implements AuthenticationOutputPort {
    private final JpaUserRepository jpaUserRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    @Override
    @NewSpan("persistence-save-user")
    public RegisterUserResult save(RegisterUserCommand command)
            throws UserAlreadyExistsException {
        log.info("initializing save in AuthenticationOutputAdapter.......");
        log.info("Command to be mapped: {}", command.getUsername());
        try {
            UserDetailsJpaEntity userDetailsJpaEntity = userPersistenceMapper
                    .toUserDetailsJpaEntity(command);
            log.info("Mapped UserDetailsJpaEntity: {}", userDetailsJpaEntity.getUsername());
            UserDetailsJpaEntity saved = jpaUserRepository
                    .save(userDetailsJpaEntity);
            RegisterUserResult result = userPersistenceMapper
                    .toRegisterUserResult(saved);
            return result;
        } catch (DataIntegrityViolationException e) {
            if(e.getMostSpecificCause().getMessage().contains("users_username_key"))
                throw new UserAlreadyExistsException("User Already exists");
            throw e;
        }
    }
}
