package com.ibrahim.Cybersecurity.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Document(collection = "admin_users")
public class AdminUser {
    @Id
    private String id;

    private String email;

    private String password;

    private String username;

    private String role = "ADMIN";

    private boolean enabled = true;

    private LocalDateTime lastLogin;

    private String accessLevel;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();
}