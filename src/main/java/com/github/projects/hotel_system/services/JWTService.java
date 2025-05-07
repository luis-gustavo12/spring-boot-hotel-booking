package com.github.projects.hotel_system.services;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.projects.hotel_system.models.User;

@Service
public class JWTService {
    
    
    private final Algorithm algorithm;
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 hours
    private final String issuer = "github.projects.HotelManagerSystem";
    
    public JWTService (@Value("${key}") String key) {
        this.algorithm = Algorithm.HMAC256(key);
    }

    public String generateToken(User user) {

        try {

            String token = JWT.create()
                .withIssuer(issuer)
                .withClaim("name", user.getName())
                .withSubject(user.getId().toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);

            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token!!");
        }


    }

    public boolean tokenIsValid(String token) {

        try {
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();

            DecodedJWT jwt = verifier.verify(token);
        } catch (JWTVerificationException ex) {
            return false;
        }

        return true;

    }


}
