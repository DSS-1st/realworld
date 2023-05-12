package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.domain.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface ArticleRepository {

    void deleteAll();

    void resetAutoIncrement();

    void persist(Article article);

    int update(Article article);

    int delete(Long id);

    Optional<Long> findMaxId();

    Optional<Article> findById(Long id);

    Optional<Article> findBySlug(String slug);
}