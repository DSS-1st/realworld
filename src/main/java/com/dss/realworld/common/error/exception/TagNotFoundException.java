package com.dss.realworld.common.error.exception;

import org.springframework.http.HttpStatus;

public class TagNotFoundException extends AbstractBaseException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public TagNotFoundException() {
        this("해당되는 Tag가 없습니다.");
    }

    public TagNotFoundException(final String message) {
        super(message);
    }
}