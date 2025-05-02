package com.github.projects.hotel_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.projects.hotel_system.models.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>{
    
}
