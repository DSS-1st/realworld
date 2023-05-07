package com.dss.realworld.article.app;

import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.domain.Article;
import com.dss.realworld.common.dto.AuthorDto;

public interface ArticleService {

    Article save(CreateArticleRequestDto createArticleRequestDto, Long logonUserId);

    void delete(String slug, Long userId);

    AuthorDto getAuthor(Long userId);
}