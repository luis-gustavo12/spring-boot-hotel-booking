package com.github.projects.hotel_system.mappers;

import java.time.LocalDate;

import com.github.projects.hotel_system.dtos.user.UserResponse;
import com.github.projects.hotel_system.dtos.user.UserUpdateResponse;
import com.github.projects.hotel_system.models.User;

public class UserMapper {
    

    public static UserResponse fromUserEntityToResponse(User user) {

        return new UserResponse(
            user.getId(), 
            user.getPreferredName().isEmpty() ? user.getName() : user.getPreferredName(),
            user.getRole(), 
            "User created successfully!!", 
            LocalDate.now());

    }

    public static UserUpdateResponse fromEntityToUpdateResponse(User user) {
        return new UserUpdateResponse(user.getName(), user.getPhoneNumber(), user.getEmail(), user.getAddress());
    }


}
