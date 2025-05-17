

package com.github.projects.hotel_system.dtos.booking;

public record BookingCreationResponse(
    long bookingId,
    long userId,
    String message,
    int status,
    String checkIn

) {
}