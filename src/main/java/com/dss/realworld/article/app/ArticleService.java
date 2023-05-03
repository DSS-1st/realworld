package com.dss.realworld.article.app;

import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.domain.dto.GetArticleDto;
import com.dss.realworld.user.domain.repository.GetUserDto;

public interface ArticleService {

    GetArticleDto create(CreateArticleRequestDto createArticleRequestDto, Long logonUserId);

    void delete(String slug, Long userId);

    GetUserDto getAuthor(Long userId);
}