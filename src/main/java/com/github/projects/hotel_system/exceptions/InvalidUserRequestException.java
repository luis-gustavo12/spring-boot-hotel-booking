package com.github.projects.hotel_system.exceptions;

public class InvalidUserRequestException extends RuntimeException {
    public InvalidUserRequestException(String msg) {
        super(msg);
    }
}
