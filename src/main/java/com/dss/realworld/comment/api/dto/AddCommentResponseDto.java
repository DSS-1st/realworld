package com.dss.realworld.comment.api.dto;

import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.common.dto.AuthorDto;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@JsonRootName(value = "comment")
@Getter
@AllArgsConstructor
public class AddCommentResponseDto {

    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String body;
    private final AuthorDto author;

    public AddCommentResponseDto(Comment foundComment, AuthorDto author) {
        this.id = foundComment.getId();
        this.createdAt = foundComment.getCreatedAt();
        this.updatedAt = foundComment.getUpdatedAt();
        this.body = foundComment.getBody();
        this.author = author;
    }
}