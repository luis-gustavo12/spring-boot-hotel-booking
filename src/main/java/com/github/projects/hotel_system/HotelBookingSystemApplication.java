package com.github.projects.hotel_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HotelBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelBookingSystemApplication.class, args);
	}

}
