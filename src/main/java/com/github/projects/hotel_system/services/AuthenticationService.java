package com.github.projects.hotel_system.services;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.projects.hotel_system.dtos.Response;
import com.github.projects.hotel_system.dtos.login.LoginRequest;
import com.github.projects.hotel_system.dtos.login.LoginResponse;
import com.github.projects.hotel_system.exceptions.UserNotFoundException;
import com.github.projects.hotel_system.models.User;
import com.github.projects.hotel_system.repositories.UserRepository;

@Service
public class AuthenticationService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final JWTService jwtService;

    public AuthenticationService(BCryptPasswordEncoder encoder, UserRepository repository, JWTService jwtService) {
        this.encoder = encoder;
        this.userRepository = repository;
        this.jwtService = jwtService;
    }

    // Authenticate login
    public LoginResponse authenticate(LoginRequest request) {

        Optional<User> optionalUser = userRepository.findByEmail(request.email());

        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User not found!!");
        } 
        
        User user = optionalUser.get();
        
        if (!encoder.matches(request.password(), user.getPassword())) {
            throw new UserNotFoundException("Password doesn't match!!");
        }

        return new LoginResponse(
            "Authenticated Successfully!!", 
            HttpStatus.OK.value(), 
            jwtService.generateToken(user) 
        );

    }



    
}
