package com.github.projects.hotel_system.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.projects.hotel_system.models.User;
import com.github.projects.hotel_system.services.AuthenticationService;
import com.github.projects.hotel_system.services.JWTService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    
    private final JWTService jwtService;
    private final AuthenticationService authenticationService;

    private final List<String> exclusionAuthRoutes = List.of("/api/login", "/api/login/", "/api/user/create", "/api/user/create/");

    public JWTAuthenticationFilter(JWTService service, AuthenticationService authenticationService) {
        this.jwtService = service;
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
    
        throws ServletException, IOException {
            
            if ( !exclusionAuthRoutes.contains(request.getRequestURI()) ) {
                if (!validateToken(request, response)) {
                    return;
                }
            }

            else if (request.getRequestURI().equals("/api/user/create") || request.getRequestURI().equals("/api/user/create/")) {
                filterChain.doFilter(request, response);
                return;
            }

            // Create Context Security Holder Context from User
            UserDetails userDetails = authenticationService.findUserContext(request.getHeader("Authorization").replace("Bearer ", ""));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

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
