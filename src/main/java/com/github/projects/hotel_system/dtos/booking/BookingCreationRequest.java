package com.github.projects.hotel_system.dtos.booking;

import java.time.LocalDateTime;

public record BookingCreationRequest(
    long hotelId,
    long roomId,
    LocalDateTime checkIn,
    LocalDateTime checkout,
    int adultsNumber,
    int kidsNumber
) {
    
}
