package com.dss.realworld.common.error.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractBaseException extends RuntimeException {

    public abstract HttpStatus getStatus();

    public AbstractBaseException() {
        super();
    }

    public AbstractBaseException(final String message) {
        super(message);
    }
}