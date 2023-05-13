package com.dss.realworld.comment.api.dto;

import com.dss.realworld.common.dto.AuthorDto;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@JsonRootName(value = "comments")
@NoArgsConstructor
public class GetCommentsResponseDto {

    private  List<CommentDto> comments;

    public GetCommentsResponseDto(List<CommentAuthorDto> commentAuthorDtoList) {
        List<CommentDto> commentDtoList = new ArrayList<>();

        for (CommentAuthorDto commentAuthorDto : commentAuthorDtoList) {
            final CommentDto commentDto = CommentDto.builder()
                    .id(commentAuthorDto.getId())
                    .createdAt(commentAuthorDto.getCreatedAt())
                    .updatedAt(commentAuthorDto.getUpdatedAt())
                    .body(commentAuthorDto.getBody())
                    .author(commentAuthorDto.getAuthor())
                    .build();
            commentDtoList.add(commentDto);
        }
        this.comments = commentDtoList;
    }

    @Getter
    public static class CommentDto {
        private final Long id;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;
        private final String body;
        private final AuthorDto author;

        @Builder
        public CommentDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String body, AuthorDto author) {
            this.id = id;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.body = body;
            this.author = author;
        }
    }
}