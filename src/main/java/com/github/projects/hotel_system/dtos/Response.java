package com.github.projects.hotel_system.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    
    private String message;
    private int status;


    public Response(String message, int status) {
        this.message = message;
        this.status = status;
    }

    

}
