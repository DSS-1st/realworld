package com.dss.realworld.common.error.exception;

import org.springframework.http.HttpStatus;

public class DuplicateFollowingException extends AbstractBaseException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

    public DuplicateFollowingException() {
        this("이미 팔로우한 사용자입니다.");
    }

    public DuplicateFollowingException(final String message) {
        super(message);
    }
}