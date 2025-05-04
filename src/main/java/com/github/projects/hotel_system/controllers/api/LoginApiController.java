package com.github.projects.hotel_system.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.projects.hotel_system.dtos.login.LoginRequest;
import com.github.projects.hotel_system.services.AuthenticationService;

@RestController
@RequestMapping("/api/login")
public class LoginApiController {

    private final AuthenticationService authenticationService;

    public LoginApiController (AuthenticationService service) {
        this.authenticationService = service;
    }
    
    @PostMapping({"", "/"})
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(
            authenticationService.authenticate(request)
        );

        
    }


}
