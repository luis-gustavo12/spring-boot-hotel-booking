package com.github.projects.hotel_system.filters;

import java.io.IOException;

import com.github.projects.hotel_system.services.JWTService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    
    private final JWTService jwtService;

    public JWTAuthenticationFilter(JWTService service) {
        this.jwtService = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
    
        throws ServletException, IOException {
            
            if (!request.getRequestURI().equals( "/api/login")) {
                if (!validateToken(request, response)) {
                    return;
                }
            }

            filterChain.doFilter(request, response);

        
    }

    private boolean validateToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            FilteringErrorHandler.handleFilterError(response, new ErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, "Token not sent!!", "Please, attach the token to the header"));
            return false;
        }
        try {
            if (!token.startsWith("Bearer ")) {
                FilteringErrorHandler.handleFilterError(response, new ErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, "Not a bearer token", "Please, send a valid bearer token"));
                return false;
            }
            token = token.replace("Bearer ", "");
            if (!jwtService.tokenIsValid(token)) {
                FilteringErrorHandler.handleFilterError(response, new ErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, "Token is not valid!!", "Please, log again!!"));
                return false;
            }
        } catch (Exception e) {
            FilteringErrorHandler.handleFilterError(response, new ErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, "Token validation error", "An error occurred during token validation"));
            return false;
        }
        return true;

    }

    
}
