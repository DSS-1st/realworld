package com.dss.realworld.comment.domain.dto;

import com.dss.realworld.user.domain.repository.GetUserDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class GetCommentAuthorDto {
    private Long id;
    private String body;
    private GetUserDto author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public GetCommentAuthorDto(Long id, String body, GetUserDto author, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.body = body;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}


