package com.example.open_telemetry_example.infrastructure.adapter.output.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
@ToString
public class UserDetailsJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    String password;
}
