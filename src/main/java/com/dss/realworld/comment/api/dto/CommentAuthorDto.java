package com.dss.realworld.comment.api.dto;

import com.dss.realworld.common.dto.AuthorDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentAuthorDto {
    private Long id;
    private String body;
    private AuthorDto author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public CommentAuthorDto(Long id, String body, AuthorDto author, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.body = body;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CommentAuthorDto of(final Long id, final String body, final AuthorDto author, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        return new CommentAuthorDto(id, body, author, createdAt, updatedAt);
    }
}