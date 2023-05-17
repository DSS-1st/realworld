package com.dss.realworld.comment.api.dto;

import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.common.dto.AuthorDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GetCommentsResponseDto {

    private final List<CommentDto> comments;

    public GetCommentsResponseDto(final List<CommentDto> comments) {
        this.comments = comments;
    }

    @Getter
    public static class CommentDto {
        private final Long id;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;
        private final String body;
        private final AuthorDto author;

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



