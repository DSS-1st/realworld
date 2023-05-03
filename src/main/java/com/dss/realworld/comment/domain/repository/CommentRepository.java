package com.dss.realworld.comment.domain.repository;

import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.comment.domain.dto.GetCommentDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentRepository {
    void add(Comment comment);

    GetCommentDto getById(Long id);

    void deleteAll();

    void resetAutoIncrement();
}
