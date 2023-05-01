package com.dss.realworld.comment.app;

import com.dss.realworld.comment.domain.dto.AddCommentRequestDto;
import com.dss.realworld.comment.domain.dto.GetCommentAuthorDto;

public interface CommentService {

    GetCommentAuthorDto addComment(AddCommentRequestDto addCommentRequestDto, Long logonUserId, String slug);
}
