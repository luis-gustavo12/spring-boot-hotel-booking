package com.github.projects.hotel_system.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.github.projects.hotel_system.exceptions.UserNotFoundException;
import com.github.projects.hotel_system.mappers.UserMapper;
import com.github.projects.hotel_system.models.User;
import com.github.projects.hotel_system.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService (UserRepository repository, PasswordEncoder encoder) {
        this.userRepository = repository;
        this.passwordEncoder = encoder;
    }
    
    
    // Create user
    public UserCreationResponse createUser (UserCreationRequest request) {

        if (request.role().equals("ADMIN")) {
            throw new InvalidUserRequestException("User type on request can't be admin!!");
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

        User createdUser = userRepository.save(user);

        UserCreationResponse response = new UserCreationResponse(createdUser.getId(), createdUser.getName(), "User created successfully!!", "token", LocalDate.now());


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


}
