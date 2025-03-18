package com.ibrahim.Cybersecurity.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ContactDTO {
    private String id;
    private String name;
    private String email;
    private String message;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String response;
    private LocalDateTime respondedAt;
    private String respondedBy;
}