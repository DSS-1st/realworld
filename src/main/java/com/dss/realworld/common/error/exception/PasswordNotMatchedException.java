package com.dss.realworld.common.error.exception;

import org.springframework.http.HttpStatus;

public class PasswordNotMatchedException extends AbstractBaseException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    public PasswordNotMatchedException() {
        this("비밀번호가 일치하지 않습니다.");
    }

    public PasswordNotMatchedException(final String message) {
        super(message);
    }
}