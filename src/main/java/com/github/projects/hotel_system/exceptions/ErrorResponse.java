package com.github.projects.hotel_system.exceptions;

public record ErrorResponse(
    String mesage,
    int statusCode
) {
    
}
