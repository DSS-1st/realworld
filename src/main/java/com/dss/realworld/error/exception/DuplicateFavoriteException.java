package com.dss.realworld.error.exception;

import org.springframework.http.HttpStatus;

public class DuplicateFavoriteException extends AbstractBaseException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public DuplicateFavoriteException() {
        this("이미 좋아한 글입니다.");
    }

    public DuplicateFavoriteException(final String message) {
        super(message);
    }
}
