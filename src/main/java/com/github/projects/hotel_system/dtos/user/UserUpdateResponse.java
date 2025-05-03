package com.github.projects.hotel_system.dtos.user;

public record UserUpdateResponse(
    String newName,
    String phoneNumber,
    String email,
    String address
) {
    
}
