package com.dss.realworld.error.exception;

import org.springframework.http.HttpStatus;

public class ArticleNotFoundException extends AbstractBaseException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public ArticleNotFoundException() {
        this("해당 게시글이 존재하지 않습니다.");
    }

    public ArticleNotFoundException(final String message) {
        super(message);
    }
}
