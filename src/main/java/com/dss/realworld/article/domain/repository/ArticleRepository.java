package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.domain.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface ArticleRepository {

    void deleteAll();

    void resetAutoIncrement();

    void persist(Article article);

    int delete(Long id);

    Long findMaxId();

    Optional<Article> findById(Long id);

    Optional<Article> findBySlug(String slug);
}
