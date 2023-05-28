package com.dss.realworld.article.app;

import com.dss.realworld.article.api.dto.ArticleListResponseDto;
import com.dss.realworld.article.api.dto.ArticleResponseDto;
import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.api.dto.UpdateArticleRequestDto;
import com.dss.realworld.common.dto.AuthorDto;

public interface ArticleService {

    ArticleResponseDto findBySlug(String slug, Long loginId);

    ArticleResponseDto save(CreateArticleRequestDto createArticleRequestDto, Long loginId);

    ArticleResponseDto update(UpdateArticleRequestDto updateArticleRequestDto, Long loginId, String slug);

    void delete(String slug, Long userId);

    AuthorDto getAuthor(Long userId, Long loginId);

    ArticleResponseDto favorite(String slug, Long loginId);

    ArticleResponseDto unfavorite(String slug, Long loginId);

    ArticleListResponseDto feed(Long loginId, int limit, int offset);
}