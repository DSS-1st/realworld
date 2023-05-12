package com.dss.realworld.comment.domain.repository;

import com.dss.realworld.comment.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentRepository {
    void add(Comment comment);

    Comment getById(Long id);

    void deleteAll();

    void resetAutoIncrement();
}
