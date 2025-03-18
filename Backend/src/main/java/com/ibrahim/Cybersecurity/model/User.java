package com.ibrahim.Cybersecurity.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @ElementCollection
    private List<String> analyzedUrls = new ArrayList<>();

    @Column(nullable = false)
    private String role = "USER";

    private boolean enabled = true;

    private LocalDateTime lastLogin;

    private LocalDateTime updatedAt;

    private LocalDateTime createdAt;

}