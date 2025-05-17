package com.github.projects.hotel_system.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.projects.hotel_system.models.TokenVerification;

@Repository
public interface TokenverificationRepository extends JpaRepository<TokenVerification, Long> {

    public Optional<TokenVerification> findByToken(String token);
    
}
