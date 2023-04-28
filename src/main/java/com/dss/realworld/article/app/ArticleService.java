package com.dss.realworld.article.app;

import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.domain.dto.GetArticleDto;
import com.dss.realworld.user.domain.repository.GetUserDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ArticleService {

    @Transactional
    GetArticleDto createArticle(CreateArticleRequestDto createArticleRequestDto);

    @Transactional
    void deleteArticle(String slug, Long userId);

    GetUserDto getArticleAuthor(Long userId);
}