package com.github.projects.hotel_system.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hotels")
@Getter
@Setter
@NoArgsConstructor
public class Hotel {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hotel_name", nullable = false, length = 80)
    private String name;

    @Column(name = "zip_address", nullable = false, length = 50)
    private String zipAddress;

    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @Column(name = "floors", nullable = false)
    private int floors;

    @Column(name = "rooms_no", nullable = false)
    private int numberOfRooms;

    @Column(name = "phone_number", length = 35)
    private String phoneNumber;

    @Column(name = "description", length = 50)
    private String description;

    @ManyToOne
    @JoinColumn(
        name = "hotel_owner_id"
    )
    private User user;

}
