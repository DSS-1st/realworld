package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.domain.ArticleTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleTagRepository {

    void persist(ArticleTag articleTag);

    ArticleTag findById(Long articleTagId);

    List<String> findTagsByArticleId(Long articleId);

    int delete(Long id);

    int deleteByArticleId(Long articleId);
}