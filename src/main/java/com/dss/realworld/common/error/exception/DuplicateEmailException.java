package com.dss.realworld.common.error.exception;

import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends AbstractBaseException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

    public DuplicateEmailException() {
        this("이미 존재하는 Email입니다.");
    }

    public DuplicateEmailException(final String message) {
        super(message);
    }
}