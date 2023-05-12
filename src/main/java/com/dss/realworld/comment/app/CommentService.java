package com.dss.realworld.comment.app;

import com.dss.realworld.comment.api.dto.AddCommentRequestDto;
import com.dss.realworld.comment.api.dto.AddCommentResponseDto;

public interface CommentService {

    AddCommentResponseDto add(AddCommentRequestDto addCommentRequestDto, Long logonUserId, String slug);

    int deleteComment(String slug,Long commentId,Long userId);
}
