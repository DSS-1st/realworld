package com.dss.realworld.error.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends AbstractBaseException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public UserNotFoundException() {
        this("해당하는 사용자를 찾을 수 없습니다.");
    }

    public UserNotFoundException(final String message) {
        super(message);
    }
}
