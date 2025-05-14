
CREATE TABLE bookings (
    id BIGINT NOT NULL AUTO_INCREMENT,
    hotel_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,
    checkin DATETIME NOT NULL,
    checkout DATETIME NOT NULL,
    adults_no TINYINT NOT NULL,
    kids_no TINYINT NOT NULL,
    total_price DECIMAL(8, 2) NOT NULL,
    booking_status_id TINYINT DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (hotel_id) REFERENCES hotels(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (room_id) REFERENCES rooms(id)
    
);