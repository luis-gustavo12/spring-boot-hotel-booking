CREATE TABLE rooms (

    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    hotel_id BIGINT,
    rooms_number TINYINT NOT NULL,
    floor_number TINYINT NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(8, 2) NOT NULL,
    width DECIMAL (8, 2) NOT NULL,
    length DECIMAL (8,2) NOT NULL,

    CONSTRAINT fk_hotel_id 
    FOREIGN KEY (hotel_id) REFERENCES hotels(id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;