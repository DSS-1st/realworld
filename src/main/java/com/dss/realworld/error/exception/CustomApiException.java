package com.dss.realworld.error.exception;

public class CustomApiException extends RuntimeException {

    public CustomApiException(final String message) {
        super(message);
    }
}