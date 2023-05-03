package com.dss.realworld.comment.domain.dto;

import com.dss.realworld.user.domain.repository.GetUserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AddCommentResponseDto {

    private AddCommentDto comment;

    public AddCommentResponseDto(GetCommentAuthorDto getCommentAuthorDto) {
        ArticleAuthorDto authorInfo = getAuthorInfo(getCommentAuthorDto.getAuthor());

        comment = AddCommentDto.builder()
                .id(getCommentAuthorDto.getId())
                .createdAt(getCommentAuthorDto.getCreatedAt())
                .updatedAt(getCommentAuthorDto.getUpdatedAt())
                .body(getCommentAuthorDto.getBody())
                .author(authorInfo)
                .build();
    }
    @Getter
    static class AddCommentDto {
        private final Long id;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;
        private final String body;
        private final ArticleAuthorDto author;

        @Builder
        public AddCommentDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String body, ArticleAuthorDto author) {
            this.id = id;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.body = body;
            this.author = author;
        }
    }

    private ArticleAuthorDto getAuthorInfo(GetUserDto getUserDto) {
        return ArticleAuthorDto.builder()
                .username(getUserDto.getUsername())
                .bio(getUserDto.getBio())
                .image(getUserDto.getImage())
                .following(false)
                .build();
    }
    @Getter
    static class ArticleAuthorDto {
        private final String username;
        private final String bio;
        private final String image;
        private final boolean following;

        @Builder
        public ArticleAuthorDto(String username, String bio, String image, boolean following) {
            this.username = username;
            this.bio = bio;
            this.image = image;
            this.following = following;
        }
    }
}
