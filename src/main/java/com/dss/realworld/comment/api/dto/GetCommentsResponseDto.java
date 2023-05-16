package com.dss.realworld.comment.api.dto;

import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.common.dto.AuthorDto;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonRootName(value = "comments")
public class GetCommentsResponseDto {

    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String body;
    private final AuthorDto author;

    private GetCommentsResponseDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String body, AuthorDto author) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.body = body;
        this.author = author;
    }

    public static GetCommentsResponseDto of(Comment comment, AuthorDto author) {
        return new GetCommentsResponseDto(comment.getId(), comment.getCreatedAt(), comment.getUpdatedAt(), comment.getBody(), author);
    }
}