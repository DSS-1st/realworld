package com.dss.realworld.article.api.dto;

import com.dss.realworld.article.domain.dto.GetArticleDto;
import com.dss.realworld.user.domain.repository.GetUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
public class CreateArticleResponseDto {

    private final CreateArticleDto article;

    @Getter
    @AllArgsConstructor
    public static class CreateArticleDto {

        private String slug;
        private String title;
        private String description;
        private String body;
        private Set<String> tagList;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private boolean favorited;
        private int favoritesCount;
        private ArticleAuthorDto author;
    }

    public CreateArticleResponseDto(GetArticleDto getArticleDto, GetUserDto getUserDto) {
        String slug = getArticleDto.getSlug();
        String title = getArticleDto.getTitle();
        String description = getArticleDto.getDescription();
        String body = getArticleDto.getBody();
        Set<String> tagList = new HashSet<>();
        LocalDateTime createdAt = getArticleDto.getCreatedAt();
        LocalDateTime updatedAt = getArticleDto.getUpdatedAt();
        ArticleAuthorDto author = new ArticleAuthorDto(getUserDto);

        article = new CreateArticleDto(slug, title, description, body, tagList, createdAt, updatedAt, false, 0, author);
    }
}
