package com.github.projects.hotel_system.services;

import java.time.LocalDateTime;

import com.github.projects.hotel_system.repositories.HotelRepository;
import com.github.projects.hotel_system.repositories.RoomRepository;

/**
 * 
 * Class supposed to check all the availability given the hotels and rooms at a certain time
 * 
 */
public class RoomAvailabilityService {


    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public RoomAvailabilityService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }


    public boolean isAvailable(long hotelId, long roomId, LocalDateTime checkIn, LocalDateTime checkout) {

        

        return false;

    }



    
}
