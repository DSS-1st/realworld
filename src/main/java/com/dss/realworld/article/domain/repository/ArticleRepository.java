package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.domain.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ArticleRepository {

    void persist(Article article);

    int update(Article article);

    int delete(Long id);

    Optional<Long> findMaxId();

    Optional<Article> findById(Long id);

    Optional<Article> findBySlug(String slug);

    List<Article> findArticleByFollower(@Param(value = "loginId") Long loginId, @Param(value = "limit") int limit, @Param(value = "offset") int offset);

    List<Article> listArticles(@Param(value = "tag") String tag, @Param(value = "author") String author, @Param(value = "favorited") String favorited, @Param(value = "limit") int limit, @Param(value = "offset") int offset);
}