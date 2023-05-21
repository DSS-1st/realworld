package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.domain.ArticleTag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleTagRepository {

    void persist(ArticleTag articleTag);

    ArticleTag findById(Long articleTagId);

    int update(ArticleTag articleTag);

    int delete(Long id);

    int deleteByArticle(Long articleId);
}