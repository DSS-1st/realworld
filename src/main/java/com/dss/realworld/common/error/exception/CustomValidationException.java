package com.dss.realworld.common.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class CustomValidationException extends AbstractBaseException {

    private final List<String> messageList;

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }

    public CustomValidationException(final List<String> messageList) {
        this.messageList = messageList;
    }
}