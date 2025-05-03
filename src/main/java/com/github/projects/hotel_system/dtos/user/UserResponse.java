package com.github.projects.hotel_system.dtos.user;

import java.time.LocalDate;

public record UserResponse(
    Long id,
    String name,
    String role,
    String message,
    LocalDate createdAt
) {
    
}
