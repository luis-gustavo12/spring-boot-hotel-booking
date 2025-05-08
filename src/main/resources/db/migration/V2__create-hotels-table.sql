CREATE TABLE hotels (

    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    hotel_name VARCHAR(80) NOT NULL,
    zip_address VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    floors TINYINT NOT NULL,
    rooms_no TINYINT NOT NULL,
    phone_number VARCHAR(35) NOT NULL,
    description VARCHAR(50) NOT NULL,
    hotel_owner_id BIGINT NOT NULL,

    FOREIGN KEY (hotel_owner_id) REFERENCES users(id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;