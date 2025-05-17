package com.github.projects.hotel_system.services;

import org.springframework.stereotype.Service;

import com.github.projects.hotel_system.models.Room;
import com.github.projects.hotel_system.repositories.RoomRepository;

@Service
public class RoomService {

    private final RoomRepository repository;


    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }


    public Room getRoomEntity(Long id) {
        return repository.findById(id).orElse(null);
    }


    
}
