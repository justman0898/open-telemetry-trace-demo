package com.example.open_telemetry_example.infrastructure.adapter.output.persistence.adapter;

import com.example.open_telemetry_example.application.ports.output.AuthenticationOutputPort;
import com.example.open_telemetry_example.domain.exceptions.UserAlreadyExistsException;
import com.example.open_telemetry_example.domain.models.RegisterUserCommand;
import com.example.open_telemetry_example.domain.models.RegisterUserResult;
import com.example.open_telemetry_example.infrastructure.adapter.output.persistence.entities.UserDetailsJpaEntity;
import com.example.open_telemetry_example.infrastructure.adapter.output.persistence.mappers.UserPersistenceMapper;
import com.example.open_telemetry_example.infrastructure.adapter.output.persistence.repositories.JpaUserRepository;
import io.micrometer.tracing.annotation.NewSpan;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import jakarta.persistence.EntityManager;
import lombok.Data;
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
    private final Tracer tracer;

    @Override
//    @NewSpan("persistence-save-user")
    public RegisterUserResult save(RegisterUserCommand command)
            throws UserAlreadyExistsException {
        Span span = tracer.spanBuilder("persistence-save-user")
                .setParent(Context.current())
                .startSpan();
        log.info("Persistence-save-user-span(Context Details): {}", Context.current());
        log.info("initializing save in AuthenticationOutputAdapter.......");
        log.info("Command to be mapped: {}", command.getUsername());

        try (Scope ignored = span.makeCurrent())
        {
            UserDetailsJpaEntity userDetailsJpaEntity = userPersistenceMapper
                    .toUserDetailsJpaEntity(command);
            log.info("Mapped UserDetailsJpaEntity: {}", userDetailsJpaEntity.getUsername());
            UserDetailsJpaEntity saved = jpaUserRepository
                    .save(userDetailsJpaEntity);
            RegisterUserResult result = userPersistenceMapper
                    .toRegisterUserResult(saved);
            span.setStatus(StatusCode.OK);
            return result;
        } catch (DataIntegrityViolationException e) {

            if(e.getMostSpecificCause().getMessage().contains("users_username_key")) {
                span.setStatus(StatusCode.ERROR, e.getMessage());
                span.recordException(e);
                throw new UserAlreadyExistsException("User Already exists");
            }
            throw e;
        }finally {
            span.end();
        }
    }
}
