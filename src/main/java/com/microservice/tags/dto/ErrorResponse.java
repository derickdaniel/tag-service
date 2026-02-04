package com.microservice.tags.dto;


public record ErrorResponse(
        String timeStamp,
        int status,
        String error,
        String message
) {

}

