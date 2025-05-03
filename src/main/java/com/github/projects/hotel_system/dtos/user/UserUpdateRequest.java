package com.github.projects.hotel_system.dtos.user;

public record UserUpdateRequest( 
    String password,
    String preferredName,
    String phoneNumber,
    String email,
    String address,
    boolean isActive) {
}
