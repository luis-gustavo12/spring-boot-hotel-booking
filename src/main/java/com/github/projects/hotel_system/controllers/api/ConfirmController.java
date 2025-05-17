package com.github.projects.hotel_system.controllers.api;

import com.github.projects.hotel_system.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.projects.hotel_system.services.TokenVerificationService;

/**
 * 
 * This class handles the tokens for when the user is registered, and it is requested to cofirm
 * the account creation on email
 * 
 */

@RestController
@RequestMapping("/api/confirm")
public class ConfirmController {

    private final TokenVerificationService tokenVerificationService;
    private final UserService userService;

    public ConfirmController (TokenVerificationService tokenVerificationService, UserService userService) {
        this.tokenVerificationService = tokenVerificationService;
        this.userService = userService;
    }
    
    
    @GetMapping({"/{token}", "/{token}/"})
    public ResponseEntity<?> confirmUser(@PathVariable String token) {
        return ResponseEntity.ok(
            userService.validateToken(token)
        );
    }


}
