package com.github.projects.hotel_system.services;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.github.projects.hotel_system.dtos.hotel.HotelCreationRequest;
import com.github.projects.hotel_system.dtos.hotel.HotelCreationResponse;
import com.github.projects.hotel_system.dtos.hotel.HotelResponse;
import com.github.projects.hotel_system.dtos.user.UserResponse;
import com.github.projects.hotel_system.exceptions.InvalidCreationRequestException;
import com.github.projects.hotel_system.exceptions.ResourceNotFoundException;
import com.github.projects.hotel_system.mappers.HotelMapper;
import com.github.projects.hotel_system.models.Hotel;
import com.github.projects.hotel_system.repositories.HotelRepository;

@Service
public class HotelService {


    private final HotelRepository hotelRepository;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    private Authentication authentication;
    private UserDetails userDetails;

    public HotelService(HotelRepository hotelRepository, AuthenticationService authenticationService,
            UserService userService) {
        this.hotelRepository = hotelRepository;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    public HotelCreationResponse createHotel(HotelCreationRequest request) {

        Hotel hotel = HotelMapper.fromHotelCreationRequestToHotel(request);

        if (!userRoleMatchesOwnerType(request) ) {
            throw new InvalidCreationRequestException("Only owners can create new hotels!! Please, create an owner account!!");
        }

        hotel.setUser( userService.findUserByEmail(userDetails.getUsername()) );
        
        hotel = hotelRepository.save(hotel);



        return new HotelCreationResponse(
            HttpStatus.CREATED.value(), 
            "Hotel registered successfully!!", 
            hotel.getId(), 
            LocalDateTime.now()
        );

    }

    private boolean userRoleMatchesOwnerType(HotelCreationRequest request) {

        authentication = SecurityContextHolder.getContext().getAuthentication();

        userDetails = (UserDetails) authentication.getPrincipal();

        UserResponse user = userService.findUserResponseByEmail(userDetails.getUsername());

        return user.role().equals("OWNER");


    }

    public List<HotelResponse> getHotels() {
        
        List<Hotel> hotels = hotelRepository.findAll();

        if (hotels.isEmpty()) {
            return List.of();
        }

        
        List<HotelResponse> responses = 
            hotels.stream()
            .map(response -> HotelMapper.fromHotelToHotelResponse(response))
            .collect(Collectors.toList());

        return responses;
    }

    public HotelResponse getHotel(Long id) {

        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel not found!!"));

        return HotelMapper.fromHotelToHotelResponse(hotel);

    }


    
}
