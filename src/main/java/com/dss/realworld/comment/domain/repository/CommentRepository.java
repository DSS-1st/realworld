package com.dss.realworld.comment.domain.repository;

import com.dss.realworld.comment.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentRepository {
    void add(Comment comment);

    Comment getById(Long id);

    void deleteAll();

    void resetAutoIncrement();

    int deleteComment(Long id,Long articleId,Long userId);
    List<Comment> getComments(Long articleId);
}
