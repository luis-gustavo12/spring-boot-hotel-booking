package com.github.projects.hotel_system.controllers.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.projects.hotel_system.dtos.booking.BookingCreationRequest;
import com.github.projects.hotel_system.dtos.booking.BookingCreationResponse;
import com.github.projects.hotel_system.services.BookingService;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping({"/api/bookings", "/api/bookings/"})
public class BookingController {

    private final BookingService bookingService;

    public BookingController (BookingService service) {
        this.bookingService = service;
    }
    

    @PostMapping({"create", "create/"})
    public ResponseEntity<?> createBooking(@RequestBody BookingCreationRequest request) {
            
        BookingCreationResponse booking = bookingService.createBooking(request);

        
        return ResponseEntity.ok(booking);
    }
    

}
