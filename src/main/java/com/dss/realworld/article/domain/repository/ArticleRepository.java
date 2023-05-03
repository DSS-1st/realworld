package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.dto.GetArticleDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface ArticleRepository {

    void deleteAll();

    void resetAutoIncrement();

    void create(Article article);

    int delete(Long id);

    Long getMaxId();

    GetArticleDto getById(Long id);

    Optional<GetArticleDto> getBySlug(String slug);
}
