package com.github.projects.hotel_system.filters;

public record ErrorResponse(
    int statusCode,
    String error,
    String message
) {
    
}
