package com.github.projects.hotel_system.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.projects.hotel_system.models.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

}
