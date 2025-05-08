package com.github.projects.hotel_system.dtos.hotel;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.github.projects.hotel_system.dtos.Response;

public record HotelCreationResponse (
    int status,
    String message,
    Long hotelId,
    LocalDateTime timestamp
) {

     
}
