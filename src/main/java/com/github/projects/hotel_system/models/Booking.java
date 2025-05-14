package com.github.projects.hotel_system.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
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
    private double totalPrice;

    

}
