package com.dss.realworld.error.exception;

import org.springframework.http.HttpStatus;

public class PasswordNotMatchedException extends AbstractBaseException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public PasswordNotMatchedException() {
        this("비밀번호가 일치하지 않습니다.");
    }

    public PasswordNotMatchedException(final String message) {
        super(message);
    }
}