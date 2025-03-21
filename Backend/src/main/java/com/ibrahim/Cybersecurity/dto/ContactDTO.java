package com.ibrahim.Cybersecurity.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ContactDTO {
    private Long id;
    private String name;
    private String email;
    private String subject;
    private String message;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String response;
    private LocalDateTime respondedAt;
    private String respondedBy;
    private Long adminUserId;
}