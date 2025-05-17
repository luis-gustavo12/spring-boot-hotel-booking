


package com.github.projects.hotel_system.services;


import java.util.Collections;
import java.util.Optional;

import javax.swing.Spring;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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
    private final EmailService emailService;
    private final TokenVerificationService tokenVerificationService;

    public AuthenticationService(BCryptPasswordEncoder encoder, UserRepository repository, 
    JWTService jwtService, EmailService emailService, TokenVerificationService tokenVerificationService) {
        this.encoder = encoder;
        this.userRepository = repository;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.tokenVerificationService = tokenVerificationService;
    }

    
    /** 
     * 
     * Authenticate the user based on email and password
     * 
     * @param request
     * @return LoginResponse -> Proper client message with token
     * @throws UserNotFoundException -> If user not found, Spring handles it
     * 
     */
    // Authenticate login
    public LoginResponse authenticate(LoginRequest request) {


        User user = userRepository.findByEmail(request.email()).orElseThrow( () -> new UserNotFoundException("User not found!!") );
        
        if (!encoder.matches(request.password(), user.getPassword())) {
            throw new UserNotFoundException("Password doesn't match!!");
        }

        // If user exists, but isn't active, generate email for confirming it

        if (!user.isUserIsConfirmed()) {
            sendConfirmationEmail(user);
            tokenVerificationService.saveToken(jwtService.generateToken(user), user);
            return new LoginResponse("Please, check your inbox to proceed", 200, null);
        }



        sendUserAuthenticationEmail(user);

        return new LoginResponse(
            "Authenticated Successfully!!", 
            HttpStatus.OK.value(), 
            jwtService.generateToken(user) 
        );

    }

    private void sendConfirmationEmail(User user) {

        String token = jwtService.generateToken(user);
        String email = String.format("<p>Dear %s, we need you to verify", user.getName() +
        " your account!!</p>");
        email += String.format("<a href=\"localhost:8080/api/confirm/%s\">Click Here<a>", token);
        email += "<br>";
        email += token;
        
        emailService.sendHtmlEmail(user.getEmail(), "Confirm your email!!", email);


    }


    /**
     * 
     * Gets the user context from Spring ContexHolder.
     * 
     * @param token The token which has the informations of the User
     * @return The UserDetails object
     * @see Spring Security Context Holder
     */
    public UserDetails findUserContext(String token) {
        
        // Get the user from the token
        Long userId = jwtService.extractIdFromToken(token);

        User user = userRepository.findById(userId).orElseThrow( () -> new UserNotFoundException("User not found!!") );

        UserDetails userDetails = loadUserByUsername(user.getEmail());

        return userDetails;
        
    }

    /**
     * 
     * Load UserDetails from email
     * 
     * @param email The user's email
     * 
     * 
     */
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

    /**
     * 
     * Send email to the user email, for when the authentication is done
     * 
     * @param user The authenticated user
     */
    private void sendUserAuthenticationEmail(User user) {


        String body = String.format("<p>Hello, %s</p>", user.getName());
        body += "<p> We've noticed a new login attempt to your account</p>";
        body += "<p>If it was you, please ignore this message. Otherwise, review your credentials, and change it as soon as possible</p>";
        body += "<p>Best regards, Security Team</p>";


        emailService.sendHtmlEmail(user.getEmail(), "New Login Attempt", body);

    }

    public User getCurrentAuthenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object obj = authentication.getPrincipal();
            if (obj instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) obj;
                try {
                    return userRepository.findByEmail(userDetails.getUsername()).orElseThrow( () -> new UserNotFoundException("User not found!!"));
                } catch (Exception e) {
                    throw new RuntimeException("Internal Error!!");
                }

            }
        }

        return null;

    }



    
}
