package com.webflux.handler;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException   {
    private final String requestPayload;

    public ValidationException(String message, String requestPayload) {
        super(message);
        this.requestPayload = requestPayload;
    }
}
