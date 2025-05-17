package com.github.projects.hotel_system.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bookings")
@Getter
@Setter
public class Booking {
    

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
        name = "hotel_id"
    )
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(
        name = "user_id"
    )
    private User user;

    @ManyToOne
    @JoinColumn(
        name = "room_id"
    )
    private Room room;

    @Column(name = "checkin")
    private LocalDateTime checkIn;

    @Column(name = "checkout")
    private LocalDateTime checkOut;

    @Column(name = "adults_no")
    private int adultsNumber;

    @Column(name = "kids_no")
    private int kidsNumber;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    
    @ManyToOne
    @JoinColumn(name = "booking_status_id")
    private BookingStatus bookingStatus;

    

}
