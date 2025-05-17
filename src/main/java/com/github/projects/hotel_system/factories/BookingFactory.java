package com.github.projects.hotel_system.factories;

import com.github.projects.hotel_system.dtos.booking.BookingCreationRequest;
import com.github.projects.hotel_system.models.Booking;
import com.github.projects.hotel_system.models.Hotel;
import com.github.projects.hotel_system.models.Room;
import com.github.projects.hotel_system.models.User;


public class BookingFactory {

    public static Booking createBooking(User user, Hotel hotel, Room room, BookingCreationRequest request)  {

        Booking booking = new Booking();

        booking.setHotel(hotel);
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckIn(request.checkIn());
        booking.setCheckOut(request.checkout());
        booking.setAdultsNumber(request.adultsNumber());
        booking.setKidsNumber(request.kidsNumber());
        booking.setTotalPrice(room.getPrice());   

        return booking;

    }
    
}
