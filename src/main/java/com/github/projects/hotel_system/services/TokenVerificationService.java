package com.github.projects.hotel_system.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.projects.hotel_system.dtos.Response;
import com.github.projects.hotel_system.exceptions.ResourceNotFoundException;
import com.github.projects.hotel_system.models.TokenVerification;
import com.github.projects.hotel_system.models.User;
import com.github.projects.hotel_system.repositories.TokenverificationRepository;


/**
 * 
 * Class related to the verifications of the /confirm path, which is for the brand new user to be created
 * 
 */

@Service
public class TokenVerificationService {


    private final TokenverificationRepository repository;
    private final JWTService jwtService;

    public TokenVerificationService (JWTService jwtService, TokenverificationRepository repository) {
        this.jwtService = jwtService;
        this.repository = repository;

    }

    public void saveToken(String rawToken, User user) {

        TokenVerification token = new TokenVerification();
        token.setUser(user);
        token.setCreatedAt(jwtService.extractCreatedAt(rawToken));
        token.setExpiresAt(jwtService.extractExpirationDateTime(rawToken));
        token.setValid(false);
        token.setToken(rawToken);

        repository.save(token);
        

    }

    public Optional<TokenVerification> findTokenByTokenNameOptional(String rawToken) {

        return repository.findByToken(rawToken);

    }

    public void setTokenValidation(TokenVerification tokenVerification) {

        repository.save(tokenVerification);

    }

    public User getUserByToken(String token) {
        
        TokenVerification dbToken = repository.findByToken(token).orElse(null);

        return dbToken.getUser();


    }




    
}
