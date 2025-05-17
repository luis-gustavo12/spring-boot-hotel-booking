package com.github.projects.hotel_system.mappers;

import com.github.projects.hotel_system.dtos.booking.BookingCreationResponse;
import com.github.projects.hotel_system.models.Booking;

public class BookingMapper {
    



    public static BookingCreationResponse fromEntityToCreationResponse (Booking entity) {


        BookingCreationResponse response = new BookingCreationResponse(
            entity.getId(),
            entity.getUser().getId(),
            "Booking finished successfully!!",
            200,
            entity.getCheckIn().toString()
        );

        return response;
    }

}
