package com.dss.realworld.common.error.exception;

import org.springframework.http.HttpStatus;

public class DuplicateUsernameException extends AbstractBaseException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

    public DuplicateUsernameException() {
        this("이미 존재하는 사용자 이름입니다.");
    }

    public DuplicateUsernameException(final String message) {
        super(message);
    }
}