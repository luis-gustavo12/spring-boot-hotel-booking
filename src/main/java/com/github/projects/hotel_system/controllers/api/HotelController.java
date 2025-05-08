package com.github.projects.hotel_system.controllers.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.projects.hotel_system.dtos.hotel.HotelCreationRequest;
import com.github.projects.hotel_system.dtos.hotel.HotelCreationResponse;
import com.github.projects.hotel_system.services.HotelService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/hotels")
public class HotelController {


    @Autowired
    private HotelService service;

    @PostMapping({"/create", "/create/"})
    public ResponseEntity<?> getAllHotels (@RequestBody @Valid HotelCreationRequest request) {

        HotelCreationResponse response = service.createHotel(request);

        return ResponseEntity.ok()
            .body(response);
    }
    


}
