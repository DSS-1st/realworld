package com.dss.realworld.article.api.dto;

import com.dss.realworld.article.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleRequestDto {

    private CreateArticleDto article;

    @Getter
    public static class CreateArticleDto {

        private final String title;
        private final String description;
        private final String body;
        private final Set<String> tagList;

        @Builder
        public CreateArticleDto(String title, String description, String body, Set<String> tagList) {
            this.title = title;
            this.description = description;
            this.body = body;
            this.tagList = tagList;
        }
    }

    public Article convertToArticle(Long logonUserId, Long maxArticleId) {
        String slugId = article.getTitle().trim().replace(" ", "-") +
                "-" + (maxArticleId + 1);

        return Article.builder()
                .slug(slugId)
                .title(article.getTitle().trim())
                .description(article.getDescription())
                .body(article.getBody())
                .userId(logonUserId)
                .build();
    }
}
