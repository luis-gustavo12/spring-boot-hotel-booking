package com.github.projects.hotel_system;

public enum BookingStatus {
    PENDING(1),
    CONFIRMED(2),
    AWAITING_PAYMENT(3),
    AWAITING_CHECKIN(4),
    ONGOING(5),
    CANCELLED(6),
    REFUNDED(7),
    MISSED(8);

    private final int value;

    BookingStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}