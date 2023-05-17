package com.dss.realworld.comment.api.dto;

import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.common.dto.AuthorDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetCommentsResponseDto {

    private List<CommentDto> comments;

    public GetCommentsResponseDto(List<CommentDto> comments) {
        this.comments = comments;
    }

    @Getter
    @NoArgsConstructor
    public static class CommentDto {
        private Long id;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String body;
        private AuthorDto author;

        private CommentDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String body, AuthorDto author) {
            this.id = id;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.body = body;
            this.author = author;
        }

        public static CommentDto of(Comment comment, AuthorDto author) {
            return new CommentDto(comment.getId(), comment.getCreatedAt(), comment.getUpdatedAt(), comment.getBody(), author);
        }
    }
}
