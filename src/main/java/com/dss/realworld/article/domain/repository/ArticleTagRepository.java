package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.domain.ArticleTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleTagRepository {

    void persist(ArticleTag articleTag);

    List<ArticleTag> findById(Long articleTagId);

    int update(ArticleTag articleTag);

    int delete(Long id);

    int deleteByArticle(Long id);
}