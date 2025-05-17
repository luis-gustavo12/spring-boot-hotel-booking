package com.github.projects.hotel_system.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.projects.hotel_system.dtos.Response;
import com.github.projects.hotel_system.dtos.user.UserCreationRequest;
import com.github.projects.hotel_system.dtos.user.UserCreationResponse;
import com.github.projects.hotel_system.dtos.user.UserResponse;
import com.github.projects.hotel_system.dtos.user.UserUpdateRequest;
import com.github.projects.hotel_system.dtos.user.UserUpdateResponse;
import com.github.projects.hotel_system.exceptions.InvalidUserRequestException;
import com.github.projects.hotel_system.exceptions.ResourceNotFoundException;
import com.github.projects.hotel_system.exceptions.UserNotFoundException;
import com.github.projects.hotel_system.mappers.UserMapper;
import com.github.projects.hotel_system.models.TokenVerification;
import com.github.projects.hotel_system.models.User;
import com.github.projects.hotel_system.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final EmailService emailService;
    private final TokenVerificationService tokenVerificationService;
    private final List<String> acceptedRoles = List.of("ADMIN", "USER", "OWNER");

    public UserService (UserRepository repository, PasswordEncoder encoder, EmailService emailService, JWTService jwtService, TokenVerificationService tokenVerificationService) {
        this.userRepository = repository;
        this.passwordEncoder = encoder;
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.tokenVerificationService = tokenVerificationService;
    }
    
    
    // Create user
    public UserCreationResponse createUser (UserCreationRequest request) {

        if (request.role().equals("ADMIN")) {
            throw new InvalidUserRequestException("User type on request can't be admin!!");
        }

        else if ( acceptedRoles.contains(request.role()) == false ) {
            throw new InvalidUserRequestException("Request role is not allowed!!");
        }

        User user = new User();
        user.setPassword(passwordEncoder.encode(request.password()));

        if (!request.preferredName().isEmpty()) 
            user.setPreferredName(request.preferredName());

        user.setName(request.name());
        user.setPhoneNumber(request.phoneNumber());
        user.setEmail(request.email());
        user.setBirthDate(request.birthDate());
        user.setRole(request.role());
        user.setAddress(request.address());
        user.setActive(true);
        user.setZipCode(request.zipCode());
        user.setUserIsConfirmed(false);

        User createdUser = userRepository.save(user);

        UserCreationResponse response = new UserCreationResponse(createdUser.getId(), createdUser.getName(), "Please, check your email to confirm your account!!", LocalDate.now());

        emailService.sendHtmlEmail(createdUser.getEmail(), "Confirm your account", this.generateConfirmEmailHTML(user));

        return response;

    }
    

    // Read user
    public UserResponse readUser(Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User wasn't found!!");
        }

        return UserMapper.fromUserEntityToResponse(optionalUser.get());

    }

    public UserResponse findUserResponseByEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow( () -> new UserNotFoundException("User not found!!"));

        return UserMapper.fromUserEntityToResponse(user);

    }

    public User findUserByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow( () -> new UserNotFoundException("User wasn't found!!"));

    }


    // Update user

    public UserUpdateResponse updateUser(UserUpdateRequest request, Long id) {
        
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User wasn't found!!"));


        if (request.password() != null) 
            user.setPassword(passwordEncoder.encode(request.password()));

        if (request.preferredName() != null) 
            user.setPreferredName(request.preferredName());

        if (request.phoneNumber() != null)
            user.setPhoneNumber(request.phoneNumber());

        if (request.email() != null) 
            user.setEmail(request.email());
        
        if (request.address() != null) 
            user.setAddress(request.address());


        user = userRepository.save(user);

        return UserMapper.fromEntityToUpdateResponse(user);
    }


    // Delete user
    public Response deleteUser(Long id) { 

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found!!");
        }

        User user = userOptional.get();

        user.setActive(false);

        userRepository.save(user);

        return new Response("User deleted successfully", HttpStatus.OK.value());

        

    }

    private String generateConfirmEmailHTML(User user) {

        String email = "<p>Dear " + user.getName() + ", please confirm your account clicking the link below!! </p>"; 
        String token = jwtService.generateToken(user);
        email += String.format("<a>localhost:8080/api/confirm/%s</a>", token);
        tokenVerificationService.saveToken(token, user);


        return email;
    }

    public void updateUserConfirmation(User user) {

        userRepository.save(user);

    }

    /**
     * 
     * Validate the incoming token from the URL, and sets the response attributes
     * 
     * @param token -> the token from the URL path
     * @return A response indicating success for the token, where on TokenVerification table,
     * it turns valid into true, and on users, isConfirmed into true as well
     */
    public Response validateToken(String token) {

        TokenVerification verification = 
            tokenVerificationService.findTokenByTokenNameOptional(token)
            .orElseThrow( 
                () -> new ResourceNotFoundException
                ("Internal error when validating your token!! Please, contact support team!!")
            );

        verification.setValid(true);
        tokenVerificationService.setTokenValidation(verification);

        User user = tokenVerificationService.getUserByToken(token);
        user.setUserIsConfirmed(true);

        userRepository.save(user);

        return new Response("Your account is now valid you can log in!!", 200);



    }


}
