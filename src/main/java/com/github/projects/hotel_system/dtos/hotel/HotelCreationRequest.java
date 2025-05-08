package com.github.projects.hotel_system.dtos.hotel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HotelCreationRequest(
    @NotBlank String name,
    @NotBlank String zipAddress,
    @NotBlank String address,
    @NotNull int floors,
    @NotNull int roomsNumber,
    @NotBlank String phoneNumber,
    @NotBlank String description
) {
    
}
