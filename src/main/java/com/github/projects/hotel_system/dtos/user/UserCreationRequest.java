
package com.github.projects.hotel_system.dtos.user;

import java.time.LocalDate;

public record UserCreationRequest(
    String password,
    String name,
    String preferredName,
    String phoneNumber,
    String email,
    LocalDate birthDate,
    String role,
    String zipCode,
    String address
) {
}