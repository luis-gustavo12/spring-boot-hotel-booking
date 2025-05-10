package com.github.projects.hotel_system.controllers.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.projects.hotel_system.dtos.hotel.HotelCreationRequest;
import com.github.projects.hotel_system.dtos.hotel.HotelCreationResponse;
import com.github.projects.hotel_system.dtos.hotel.HotelResponse;
import com.github.projects.hotel_system.services.HotelService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    
    private final HotelService service;

    public HotelController(HotelService service) {
        this.service = service;
    }


    @PostMapping({"/create", "/create/"})
    public ResponseEntity<?> getAllHotels (@RequestBody @Valid HotelCreationRequest request) {

        HotelCreationResponse response = service.createHotel(request);

        return ResponseEntity.ok()
            .body(response);
    }
    
    @GetMapping({"/", ""})
    public ResponseEntity<?> listAvailableHotels() {

        List<HotelResponse> hotels = service.getHotels();

        return ResponseEntity.ok()
            .body(hotels);
    }

    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity<?> listSingleHotel(@PathVariable String id) {

        HotelResponse response = service.getHotel(Long.parseLong(id));

        return ResponseEntity.ok().body(response);
    }
    
    


}
