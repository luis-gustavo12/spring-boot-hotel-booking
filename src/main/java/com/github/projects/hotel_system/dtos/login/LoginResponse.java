package com.github.projects.hotel_system.dtos.login;

import com.github.projects.hotel_system.dtos.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse extends Response {

    private String token;

    public LoginResponse(String message, int status, String token) {
        super(message, status);
        this.token = token;
    }
    
}
