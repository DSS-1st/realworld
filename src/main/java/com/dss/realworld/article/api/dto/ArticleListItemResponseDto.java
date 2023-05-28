package com.dss.realworld.article.api.dto;

import com.dss.realworld.common.dto.AuthorDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ArticleListItemResponseDto {

    private final String slug;
    private final String title;
    private final String description;
    private final String body;
    private final List<String> tagList;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final boolean favorited;
    private final int favoritesCount;
    private final AuthorDto author;

    public ArticleListItemResponseDto(ArticleDtoBinder articleDtoBinder) {
        this.slug = articleDtoBinder.getContent().getSlug();
        this.title = articleDtoBinder.getContent().getTitle();
        this.description = articleDtoBinder.getContent().getDescription();
        this.body = articleDtoBinder.getContent().getBody();
        this.tagList = articleDtoBinder.getTagList();
        this.createdAt = articleDtoBinder.getContent().getCreatedAt();
        this.updatedAt = articleDtoBinder.getContent().getUpdatedAt();
        this.favorited = articleDtoBinder.isFavorited();
        this.favoritesCount = articleDtoBinder.getFavoritesCount();
        this.author = articleDtoBinder.getAuthor();
    }
}