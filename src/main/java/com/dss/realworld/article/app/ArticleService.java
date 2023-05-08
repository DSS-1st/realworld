package com.dss.realworld.article.app;

import com.dss.realworld.article.api.dto.ArticleResponseDto;
import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.common.dto.AuthorDto;

public interface ArticleService {

    ArticleResponseDto findBySlug(String slug);

    ArticleResponseDto save(CreateArticleRequestDto createArticleRequestDto, Long loginUserId);

    void delete(String slug, Long userId);

    AuthorDto getAuthor(Long userId);
}