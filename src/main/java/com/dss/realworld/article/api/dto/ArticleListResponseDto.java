package com.dss.realworld.article.api.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ArticleListResponseDto {

    private final List<ArticleListItemResponseDto> articles;

    private final int articlesCount;

    public ArticleListResponseDto(final List<ArticleListItemResponseDto> articles) {
        this.articles = articles;
        this.articlesCount = articles.size();
    }

    public ArticleListResponseDto(final int articlesCount) {
        this.articles = new ArrayList<>();
        this.articlesCount = articlesCount;
    }
}