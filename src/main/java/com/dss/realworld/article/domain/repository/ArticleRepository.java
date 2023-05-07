package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.dto.GetArticleDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleRepository {

    void deleteAll();

    void resetAutoIncrement();

    void createArticle(Article article);

    int deleteArticle(Long id);

    Long getMaxArticleId();

    GetArticleDto getArticleById(Long id);

    GetArticleDto getArticleBySlug(String slug);
}
