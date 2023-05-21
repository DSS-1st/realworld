package com.dss.realworld.comment.domain.repository;

import com.dss.realworld.comment.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentRepository {
    void add(Comment comment);

    Comment getById(Long id);

    int delete(@Param(value = "id") Long id, @Param(value = "articleId") Long articleId, @Param(value = "userId") Long userId);

    List<Comment> getAll(Long articleId);
}
