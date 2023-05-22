package com.dss.realworld.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PasswordNotMatchedException extends RuntimeException{

    public PasswordNotMatchedException() {
        super("비밀번호가 일치하지 않습니다");
    }
}