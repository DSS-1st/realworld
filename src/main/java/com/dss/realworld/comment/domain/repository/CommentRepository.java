package com.dss.realworld.comment.domain.repository;

import com.dss.realworld.comment.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentRepository {
    void persist(Comment comment);

    Optional<Comment> findById(Long id);

    int delete(@Param(value = "id") Long id, @Param(value = "articleId") Long articleId, @Param(value = "userId") Long userId);

    int deleteByArticleId(Long articleId);

    List<Comment> findAll(Long articleId);
}
