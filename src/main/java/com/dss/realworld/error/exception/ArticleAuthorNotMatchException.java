package com.dss.realworld.error.exception;

import org.springframework.http.HttpStatus;

public class ArticleAuthorNotMatchException extends AbstractBaseException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public ArticleAuthorNotMatchException() {
        this("작성자가 일치하지 않습니다.");
    }

    public ArticleAuthorNotMatchException(final String message) {
        super(message);
    }
}
