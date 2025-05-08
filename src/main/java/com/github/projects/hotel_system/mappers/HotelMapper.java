package com.github.projects.hotel_system.mappers;

import com.github.projects.hotel_system.dtos.hotel.HotelCreationRequest;
import com.github.projects.hotel_system.models.Hotel;

public class HotelMapper {

    public static Hotel fromHotelCreationRequestToHotel(HotelCreationRequest request) {

        Hotel hotel = new Hotel();

        hotel.setName(request.name());
        hotel.setZipAddress(request.zipAddress());
        hotel.setAddress(request.address());
        hotel.setFloors(request.floors());
        hotel.setNumberOfRooms(request.roomsNumber());
        hotel.setPhoneNumber(request.phoneNumber());
        hotel.setDescription(request.description());

        return hotel;
    }
    
}
