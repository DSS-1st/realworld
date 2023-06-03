package com.dss.realworld.common.error.exception;

import org.springframework.http.HttpStatus;

public class DuplicateFavoriteException extends AbstractBaseException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

    public DuplicateFavoriteException() {
        this("이미 좋아한 글입니다.");
    }

    public DuplicateFavoriteException(final String message) {
        super(message);
    }
}