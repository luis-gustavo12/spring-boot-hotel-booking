package com.github.projects.hotel_system.dtos.user;

import java.time.LocalDate;

public record UserCreationResponse(
    Long id,
    String preferredName,
    String message, 
    String token,
    LocalDate createdAt
) {
}
