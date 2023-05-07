package com.dss.realworld.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArticleAuthorNotMatchException extends RuntimeException {

    public ArticleAuthorNotMatchException() {
        super("작성자가 일치하지 않습니다.");
    }
}
