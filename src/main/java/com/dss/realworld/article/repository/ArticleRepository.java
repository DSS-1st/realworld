package com.dss.realworld.article.repository;

import com.dss.realworld.article.domain.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleRepository {

    void createArticle(Article article);
}
