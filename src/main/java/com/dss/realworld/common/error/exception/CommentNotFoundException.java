package com.dss.realworld.common.error.exception;

import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends AbstractBaseException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public CommentNotFoundException() {
        this("댓글이 존재하지 않습니다.");
    }

    public CommentNotFoundException(final String message) {
        super(message);
    }
}