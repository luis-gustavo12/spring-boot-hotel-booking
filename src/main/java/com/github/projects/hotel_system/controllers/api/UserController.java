package com.github.projects.hotel_system.controllers.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.projects.hotel_system.dtos.user.UserCreationRequest;
import com.github.projects.hotel_system.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController (UserService service) {
        this.userService = service;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserCreationRequest request) {
        
        return ResponseEntity.ok()
            .body(userService.createUser(request));
        
    }
    
    
}
