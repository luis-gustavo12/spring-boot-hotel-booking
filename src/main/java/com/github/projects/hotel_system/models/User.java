package com.github.projects.hotel_system.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "name", length = 90)
    private String name;

    @Column(name = "preferred_name", nullable = true, length = 30)
    private String preferredName;

    @Column(name = "phone_number", length = 20, unique = true)
    private String phoneNumber;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "birth_dte")
    private LocalDate birthDate;

    @Column(name = "role", length = 50)
    private String role;

    @Column(name = "address", length = 40)
    private String address;

    @Column(name = "is_active")
    private boolean isActive;

}
