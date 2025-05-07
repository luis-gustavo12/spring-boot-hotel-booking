package com.github.projects.hotel_system.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.github.projects.hotel_system.dtos.user.UserCreationRequest;
import com.github.projects.hotel_system.dtos.user.UserCreationResponse;
import com.github.projects.hotel_system.exceptions.InvalidUserRequestException;
import com.github.projects.hotel_system.models.User;
import com.github.projects.hotel_system.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService service;

    private User user;

    @BeforeEach
    @DisplayName("Given")
    void setUp() {
        user = new User();
        user.setPassword("pwd123");
        user.setName("John Doe");
        user.setPreferredName("John");
        user.setPhoneNumber("123123123");
        user.setEmail("john.doe@example.com");
        user.setBirthDate(LocalDate.of(1980, 2, 15));
        user.setRole("USER");
        user.setAddress("Random Avenue 125");
        user.setZipCode("2131232132131");
        user.setActive(true);

        repository.save(user);
    }

    @Test
    @DisplayName("Test error on request for creating admin user!!")
    void testAdminErrorOnRequestCreation() {

        // When
        UserCreationRequest request = new UserCreationRequest(
            "pwd213", 
            "Alicia Simons", 
            "Alicia", 
            "+123123123123", 
            "alicia@example.com", 
            LocalDate.of(1985, 2, 23), 
            "ADMIN", 
            "25252", 
            "Random Avenue"
        );

        // Then
        InvalidUserRequestException exception = assertThrows(
            InvalidUserRequestException.class,
            () -> service.createUser(request)
        );
        
        assertThat(exception.getMessage()).isEqualTo("User type on request can't be admin!!");
        


    }

    @Test
    @DisplayName("Testing user regular creation!!")
    void testRegularUserCreation() {

        // When
        UserCreationRequest request = new UserCreationRequest(
            "pwd213", 
            "Alicia Simons", 
            "Alicia", 
            "+123123123123", 
            "alicia@example.com", 
            LocalDate.of(1985, 2, 23), 
            "USER", 
            "25252", 
            "Random Avenue"
        );

        // Then

        UserCreationResponse response = service.createUser(request);

        assertThat(response.message()).isEqualTo("User created successfully!!");
        assertThat(response.token()).isNotEmpty();

        LocalDate expectedDate = LocalDate.now();

        assertThat(response.createdAt()).isEqualTo(expectedDate);

    }


    
}
