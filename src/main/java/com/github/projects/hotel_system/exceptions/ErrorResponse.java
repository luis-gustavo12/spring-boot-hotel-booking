package com.github.projects.hotel_system.exceptions;

import java.time.Instant;

public record ErrorResponse(
    String message,
    int statusCode,
    Instant timestamp
) {

    public ErrorResponse(String message, int statusCode) {
        this(message, statusCode, Instant.now());
    }
    
}
