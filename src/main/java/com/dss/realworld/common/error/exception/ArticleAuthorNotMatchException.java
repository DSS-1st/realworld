package com.dss.realworld.common.error.exception;

import org.springframework.http.HttpStatus;

public class ArticleAuthorNotMatchException extends AbstractBaseException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public ArticleAuthorNotMatchException() {
        this("작성자가 일치하지 않습니다.");
    }

    public ArticleAuthorNotMatchException(final String message) {
        super(message);
    }
}
