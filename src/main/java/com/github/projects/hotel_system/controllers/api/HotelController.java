package com.github.projects.hotel_system.controllers.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/hotels")
public class HotelController {


    @GetMapping("/")
    public ResponseEntity<?> getAllHotels (@RequestParam String param) {
        return null;
    }
    


}
