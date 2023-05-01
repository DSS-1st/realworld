package com.dss.realworld.comment.domain.repository;

import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.comment.domain.dto.GetCommentDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentRepository {
    void addComment(Comment comment);

    GetCommentDto getCommentById(Long id);

    void deleteAll();

    void resetAutoIncrement();
}
