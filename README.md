# Hotel Booking System

A web application for searching hotels, viewing room availability, and booking rooms. Built with Spring Boot.

---

## Features

- User registration and login
- Search hotels and rooms by location and date
- Book and manage reservations
- Admin panel for hotel and room management
- Role-based access control (user, admin)
- Email notifications for bookings (optional)
- Responsive web interface with Thymeleaf

---

## Technologies

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security
- MySQL
- Lombok
- Spring Mail

---

## Getting Started

### Prerequisites

- Java 17
- Graddle
- MySQL 8

### Installation


1. **Clone the repository:**
   ```bash
   git clone https://github.com/luis-gustavo12/spring-boot-hotel-booking .
   ```

2. **Set up database:**
    Make sure to have MySQL installed, and set for using. Then, create the database
    ```SQL
    CREATE DATABASE hotel_reservation; 
    ```

3. **Set application.properties file for recognizing and accessing the database:**
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/hotel_reservation
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
    ```
        

4. **Navigate to the project directory**
    ```bash
    cd hotel-booking-system
    ```

5. **Build the project**
    ```bash
    ./gradlew bootRun
    ```



### License
This project is licensed under the MIT License.

#### Contact

https://linkedin.com/luis-gustavo12