package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.domain.ArticleUsers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleUsersRepository {

    void persist(ArticleUsers articleUsers);

    ArticleUsers findById(Long id);

    int deleteArticleRelation(Long articleId);

    int delete(@Param(value = "articleId") Long articleId, @Param(value = "loginId") Long loginId);

    int isFavorite(@Param(value = "articleId") Long articleId, @Param(value = "loginId") Long loginId);

    int findCountByArticleId(Long articleId);
}