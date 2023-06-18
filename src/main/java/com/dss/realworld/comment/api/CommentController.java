package com.dss.realworld.comment.api;

import com.dss.realworld.comment.api.dto.AddCommentRequestDto;
import com.dss.realworld.comment.api.dto.AddCommentResponseDto;
import com.dss.realworld.comment.api.dto.CommentDto;
import com.dss.realworld.comment.api.dto.CommentListResponseDto;
import com.dss.realworld.comment.app.CommentService;
import com.dss.realworld.common.auth.LoginUser;
import com.dss.realworld.common.error.CustomExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "/api/articles")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/{slug}/comments")
    public ResponseEntity<AddCommentResponseDto> add(@RequestBody @Valid final AddCommentRequestDto addCommentRequestDto, final BindingResult bindingResult,
                                                     @AuthenticationPrincipal final LoginUser loginUser,
                                                     @PathVariable final String slug) {
        CustomExceptionHandler.checkOrThrow(bindingResult);

        AddCommentResponseDto addCommentResponseDto = commentService.add(addCommentRequestDto, loginUser.getUser().getId(), slug);

        return ResponseEntity.status(HttpStatus.CREATED).body(addCommentResponseDto);
    }

    @DeleteMapping(value = "{slug}/comments/{commentId}")
    public ResponseEntity delete(@PathVariable final String slug,
                                 @PathVariable final Long commentId,
                                 @AuthenticationPrincipal final LoginUser loginUser) {
        commentService.delete(slug, commentId, loginUser.getUser().getId());

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{slug}/comments")
    public ResponseEntity<CommentListResponseDto> get(@PathVariable final String slug,
                                                      @AuthenticationPrincipal final LoginUser loginUser) throws JsonProcessingException {
        List<CommentDto> commentList;
        if (loginUser == null) commentList = commentService.getAll(slug);
        else commentList = commentService.getAll(slug, loginUser.getUser().getId());
        CommentListResponseDto comments = new CommentListResponseDto(commentList);

        return ResponseEntity.status(HttpStatus.CREATED).body(comments);
    }
}