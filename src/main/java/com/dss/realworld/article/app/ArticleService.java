package com.dss.realworld.article.app;

import com.dss.realworld.common.dto.AuthorDto;
import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.domain.dto.GetArticleDto;

public interface ArticleService {

    GetArticleDto save(CreateArticleRequestDto createArticleRequestDto, Long logonUserId);

    void delete(String slug, Long userId);

    AuthorDto getAuthor(Long userId);
}