package com.dss.realworld.error.exception;

import org.springframework.http.HttpStatus;

public class SelfFollowingException extends AbstractBaseException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public SelfFollowingException() {
        this("자신을 팔로우할 수 없습니다.");
    }

    public SelfFollowingException(final String message) {
        super(message);
    }
}
