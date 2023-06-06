package com.dss.realworld.common.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ErrorResponse {

    private final int statusCode;
    private final String statusReason;
    private final Errors errors;

    @Getter
    public static final class Errors {

        private final List<String> body;

        @Builder
        public Errors(final List<String> body) {
            this.body = body;
        }
    }

    public ErrorResponse(final int statusCode, final String statusReason, final String detailMessage) {
        this.statusCode = statusCode;
        this.statusReason = statusReason;
        this.errors = Errors.builder().body(List.of(detailMessage)).build();
    }

    public ErrorResponse(final int statusCode, final String statusReason, final List<String> messageList) {
        this.statusCode = statusCode;
        this.statusReason = statusReason;
        this.errors = Errors.builder().body(messageList).build();
    }
}