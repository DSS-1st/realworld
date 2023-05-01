package com.dss.realworld.comment.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddCommentRequestDto {

    private AddCommentDto comment;

    public AddCommentRequestDto(){}

    @Builder
    public AddCommentRequestDto(AddCommentDto comment) {
        this.comment = comment;
    }

    @Getter
    public static class AddCommentDto {
        private String body;

        public AddCommentDto(){}

        @Builder
        public AddCommentDto(String body) {
            this.body = body;
        }
    }
}
