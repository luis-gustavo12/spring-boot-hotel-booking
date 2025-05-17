package com.github.projects.hotel_system.services;

import com.github.projects.hotel_system.BookingStatus;
import com.github.projects.hotel_system.factories.BookingFactory;
import com.github.projects.hotel_system.mappers.BookingMapper;
import com.github.projects.hotel_system.models.Booking;
import org.springframework.stereotype.Service;

import com.github.projects.hotel_system.dtos.booking.BookingCreationRequest;
import com.github.projects.hotel_system.dtos.booking.BookingCreationResponse;
import com.github.projects.hotel_system.exceptions.ResourceNotFoundException;
import com.github.projects.hotel_system.exceptions.TokenExpirationException;
import com.github.projects.hotel_system.models.Hotel;
import com.github.projects.hotel_system.models.Room;
import com.github.projects.hotel_system.models.User;
import com.github.projects.hotel_system.repositories.BookingRepository;

@Service
public class BookingService {

    private final BookingRepository repository;
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final HotelService hotelService;
    private final RoomService roomService;


    public BookingService(BookingRepository repository, UserService userService, AuthenticationService authenticationService, HotelService hotelService, RoomService roomService) {
        this.repository = repository;
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.hotelService = hotelService;
        this.roomService = roomService;
    }

    
    
    // Create

    public BookingCreationResponse createBooking(BookingCreationRequest request) {

        
        User currentUser = authenticationService.getCurrentAuthenticatedUser();

        if (currentUser == null)  {
            throw new TokenExpirationException("Please, log in again!!");
        }
        
        Hotel hotel = hotelService.getHotelEntity(request.hotelId());

        if (hotel == null) {
            throw new ResourceNotFoundException("Hotel not found!! Please, check request details");
        }

        Room room = roomService.getRoomEntity(request.roomId());

        if (room == null) {
            throw new ResourceNotFoundException("Room not found!! Please, check your request!!");
        }

        Booking booking = BookingFactory.createBooking(currentUser, hotel, room, request);

        booking.getBookingStatus().setId((long)BookingStatus.PENDING.getValue());

        booking = repository.save(booking);

        return BookingMapper.fromEntityToCreationResponse(booking);
    }

    // Read

    // Update

    //Delete

}
