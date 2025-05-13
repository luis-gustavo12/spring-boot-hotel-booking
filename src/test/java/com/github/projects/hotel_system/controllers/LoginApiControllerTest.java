package com.github.projects.hotel_system.controllers;

import com.github.projects.hotel_system.dtos.login.LoginResponse;
import com.github.projects.hotel_system.services.JWTService;
import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.github.projects.hotel_system.builders.LoginRequestBuilder;
import com.github.projects.hotel_system.controllers.api.LoginApiController;
import com.github.projects.hotel_system.dtos.login.LoginRequest;
import com.github.projects.hotel_system.services.AuthenticationService;
import com.google.gson.Gson;

import org.springframework.http.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginApiController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LoginApiControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JWTService jwtService;

    private LoginRequest request;


    @BeforeEach
    public void setUp() {

        
        request = LoginRequestBuilder.builder()
            .email("example@email.com")
            .password("password123")
            .build();


    }

    @Test
    public void perform() throws Exception{

        Gson gson = new Gson();

        String content = gson.toJson(request);

        when(authenticationService.authenticate(any(LoginRequest.class)))
                .thenReturn(new LoginResponse("Authenticated Successfully!!!", 200, "token"));
        
        ResultActions actions = mockMvc.perform(
            post("/api/login/")
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .content(content)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(200))
        .andExpect(jsonPath("$.message").value("Authenticated Successfully!!!"));


    }


    
}
