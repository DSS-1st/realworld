package com.dss.realworld.article.api.dto;

import com.dss.realworld.common.dto.AuthorDto;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleDtoBinder {

    private final ArticleContentDto content;
    private final AuthorDto author;
    private final List<String> tagList;
    private final boolean favorited;
    private final int favoritesCount;

    public ArticleDtoBinder(final ArticleContentDto content, final AuthorDto author, final List<String> tagList, final boolean favorited, final int favoritesCount) {
        this.content = content;
        this.author = author;
        this.tagList = tagList;
        this.favorited = favorited;
        this.favoritesCount = favoritesCount;
    }
}