package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.domain.dto.GetArticleDto;
import com.dss.realworld.article.domain.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleRepository {

    void createArticle(Article article);

    GetArticleDto getArticleById(Long id);

    GetArticleDto getArticleBySlug(String slug);
}
