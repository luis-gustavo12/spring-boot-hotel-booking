package com.github.projects.hotel_system.dtos.hotel;

public record HotelResponse(
    String name,
    String zipAddress,
    int floors,
    int rooms,
    String contact,
    String description,
    String email
) {
    
}
