CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(90) NOT NULL,
    preferred_name VARCHAR(30),
    phone_number VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    birth_dte DATE NOT NULL,
    role VARCHAR(50) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    address VARCHAR(40) NOT NULL,
    is_active TINYINT(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;