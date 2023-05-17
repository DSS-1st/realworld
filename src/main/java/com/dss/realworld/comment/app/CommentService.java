package com.dss.realworld.comment.app;

import com.dss.realworld.comment.api.dto.AddCommentRequestDto;
import com.dss.realworld.comment.api.dto.AddCommentResponseDto;
import com.dss.realworld.comment.api.dto.CommentDto;
import com.dss.realworld.common.dto.AuthorDto;

import java.util.List;

public interface CommentService {

    AddCommentResponseDto add(AddCommentRequestDto addCommentRequestDto, Long logonUserId, String slug);

    int delete(String slug, Long commentId, Long userId);

    AuthorDto getAuthor(Long userId);

    List<CommentDto> getAll(String slug);
}