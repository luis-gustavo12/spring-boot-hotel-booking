package com.github.projects.hotel_system.builders;

import java.time.LocalDate;

import com.github.projects.hotel_system.models.User;

public class UserBuilder {

    private Long id;
    private String password;
    private String name;
    private String preferredName;
    private String phoneNumber;
    private String email;
    private LocalDate birthDate;
    private String role;
    private String address;
    private String zipCode;
    private boolean isActive;
    private boolean isConfirmed;
    
    public static UserBuilder builder () {
        return new UserBuilder();
    }

    public UserBuilder id (Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder preferredName(String name) {
        this.preferredName = name;
        return this;
    }

    public UserBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public UserBuilder role(String role) {
        this.role = role;
        return this;
    }

    public UserBuilder address (String address) {
        this.address = address;
        return this;
    }

    public UserBuilder zipCode (String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public UserBuilder isActive(boolean value) {
        this.isActive = value;
        return this;
    }

    public UserBuilder isConfirmed (boolean value) {
        this.isConfirmed(value);
        return  this;
    }

    public User build() {
        return new User(
            this.id,
            this.password,
            this.name,
            this.preferredName,
            this.phoneNumber,
            this.email,
            this.birthDate,
            this.role,
            this.address,
            this.zipCode,
            this.isActive,
            this.isConfirmed
        );
    }
    

}
