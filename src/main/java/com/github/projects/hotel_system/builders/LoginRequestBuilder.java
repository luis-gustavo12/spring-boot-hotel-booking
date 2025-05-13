package com.github.projects.hotel_system.builders;

import com.github.projects.hotel_system.dtos.login.LoginRequest;

public class LoginRequestBuilder {

    private String email;
    private String password;

    public static LoginRequestBuilder builder() {
        return new LoginRequestBuilder();
    }

    public LoginRequestBuilder email(String email) {
        this.email = email;
        return this;
    }

    public LoginRequestBuilder password(String password) {
        this.password = password;
        return this;
    }

    public LoginRequest build() {
        return new LoginRequest(this.email, this.password);
    }
    
    
}
