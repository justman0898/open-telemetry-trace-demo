package com.example.open_telemetry_example.infrastructure.adapter.output.persistence.repositories;

import com.example.open_telemetry_example.infrastructure.adapter.output.persistence.entities.UserDetailsJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserDetailsJpaEntity, UUID> {
}
