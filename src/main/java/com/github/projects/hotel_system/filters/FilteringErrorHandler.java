package com.github.projects.hotel_system.filters;

import java.io.IOException;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletResponse;

public class FilteringErrorHandler {

    private static final Gson gson = new Gson();


    public static void handleFilterError(HttpServletResponse response, ErrorResponse error) throws IOException {

    

        response.setStatus(error.statusCode());
        response.setContentType("application/json");

        try {
            response.getWriter().write(gson.toJson(error));
        } catch (Exception e) {
            String responseString = new String("{\"status\":500,\"message\":\"Internal Error\"}");
            response.getWriter().write(responseString);
        }
       

    }

    
}
