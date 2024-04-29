package com.voc.panchayath.api;

public class ApiError extends Exception {

    public ApiError(String message) {
        super(message);
    }

    public ApiError(String message, Throwable cause) {
        super(message, cause);
    }
}
