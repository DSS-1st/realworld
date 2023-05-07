package com.dss.realworld.article.app;

import com.dss.realworld.article.api.dto.ArticleAuthorDto;
import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.domain.dto.GetArticleDto;

public interface ArticleService {

    GetArticleDto save(CreateArticleRequestDto createArticleRequestDto, Long logonUserId);

    void delete(String slug, Long userId);

    ArticleAuthorDto getAuthor(Long userId);
}