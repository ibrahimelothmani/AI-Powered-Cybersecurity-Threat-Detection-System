package com.ibrahim.Cybersecurity.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminUserDTO {
    private String id;
    private String email;
    private String username;
    private String role;
    private boolean enabled;
    private LocalDateTime lastLogin;
    private String accessLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}