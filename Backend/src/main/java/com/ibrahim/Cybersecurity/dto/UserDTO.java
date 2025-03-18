package com.ibrahim.Cybersecurity.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private String role;
    private boolean enabled;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}