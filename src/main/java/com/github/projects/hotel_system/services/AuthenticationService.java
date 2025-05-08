package com.github.projects.hotel_system.services;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.projects.hotel_system.dtos.login.LoginRequest;
import com.github.projects.hotel_system.dtos.login.LoginResponse;
import com.github.projects.hotel_system.exceptions.UserNotFoundException;
import com.github.projects.hotel_system.models.User;
import com.github.projects.hotel_system.repositories.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {

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

    public UserDetails findUserContext(String token) {
        
        // Get the user from the token
        Long userId = jwtService.extractIdFromToken(token);

        User user = userRepository.findById(userId).orElseThrow( () -> new UserNotFoundException("User not found!!") );

        UserDetails userDetails = loadUserByUsername(user.getEmail());

        return userDetails;
        
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        User user = userRepository.findByEmail(email).orElseThrow( () -> new UsernameNotFoundException("User wasn't found!!") );

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            Collections.singletonList(authority)
        );

    }



    
}
