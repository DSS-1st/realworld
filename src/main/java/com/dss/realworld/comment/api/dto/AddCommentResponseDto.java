package com.dss.realworld.comment.api.dto;

import com.dss.realworld.comment.domain.dto.GetCommentDto;
import com.dss.realworld.user.domain.User;
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
    private final CommentAuthorDto author;

    public AddCommentResponseDto(GetCommentDto foundComment, User foundUser) {
        this.id = foundComment.getId();
        this.createdAt = foundComment.getCreatedAt();
        this.updatedAt = foundComment.getUpdatedAt();
        this.body = foundComment.getBody();
        this.author = new CommentAuthorDto(foundUser.getUsername(), foundUser.getBio(), foundUser.getImage());
    }
}