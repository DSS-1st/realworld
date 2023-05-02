package com.dss.realworld.comment.api;

import com.dss.realworld.comment.app.CommentService;
import com.dss.realworld.comment.domain.dto.AddCommentRequestDto;
import com.dss.realworld.comment.domain.dto.AddCommentResponseDto;
import com.dss.realworld.comment.domain.dto.GetCommentAuthorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/articles")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "{slug}/comments")
    public AddCommentResponseDto addComment(@RequestBody AddCommentRequestDto addCommentRequestDto,
                                            @PathVariable String slug) {
        GetCommentAuthorDto savedComment = commentService.addComment(addCommentRequestDto,getLogonUserId(),slug);

        return new AddCommentResponseDto(savedComment);
    }

    // todo SecurityContextHolder에서 인증 정보 얻기
    private Long getLogonUserId(){
        return 1L;
    }
}
