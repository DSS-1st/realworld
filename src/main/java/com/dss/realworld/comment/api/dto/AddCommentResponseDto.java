package com.dss.realworld.comment.api.dto;

import com.dss.realworld.comment.domain.dto.GetCommentDto;
import com.dss.realworld.user.domain.repository.GetUserDto;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonRootName(value = "comment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentResponseDto {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String body;
    private CommentAuthorDto author;

    public AddCommentResponseDto(GetCommentDto foundComment, GetUserDto foundUser){
        this.id = foundComment.getId();
        this.createdAt = foundComment.getCreatedAt();
        this.updatedAt = foundComment.getUpdatedAt();
        this.body = foundComment.getBody();
        this.author = new CommentAuthorDto(foundUser);
    }
}