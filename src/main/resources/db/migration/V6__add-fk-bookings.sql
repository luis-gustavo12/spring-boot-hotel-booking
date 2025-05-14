

ALTER TABLE bookings
ADD CONSTRAINT fk_booking_status
FOREIGN KEY (booking_status_id)
REFERENCES booking_status(id);