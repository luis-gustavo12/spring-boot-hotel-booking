package com.github.projects.hotel_system.exceptions;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
    String message,
    int statusCode,
    Instant timestamp,
    List<FieldListerResponse> errors
) {

    public ErrorResponse(String message, int statusCode) {
        this(message, statusCode, Instant.now(), null);
    }

    public ErrorResponse(String message, int statusCode, List<FieldListerResponse> errors) {
        this(message, statusCode, Instant.now(), errors);
    }
    
}
