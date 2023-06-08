package com.dss.realworld.comment.api;

import com.dss.realworld.comment.api.dto.AddCommentRequestDto;
import com.dss.realworld.comment.api.dto.AddCommentResponseDto;
import com.dss.realworld.comment.api.dto.CommentListResponseDto;
import com.dss.realworld.comment.app.CommentService;
import com.dss.realworld.common.auth.LoginUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/api/articles")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/{slug}/comments")
    public ResponseEntity<AddCommentResponseDto> create(@RequestBody @Valid final AddCommentRequestDto addCommentRequestDto, final BindingResult bindingResult,
                                                        @AuthenticationPrincipal final LoginUser loginUser,
                                                        @PathVariable final String slug) {
        AddCommentResponseDto addCommentResponseDto = commentService.add(addCommentRequestDto, loginUser.getUser().getId(), slug);

        return ResponseEntity.status(HttpStatus.CREATED).body(addCommentResponseDto);
    }

    @DeleteMapping(value = "{slug}/comments/{id}")
    public ResponseEntity delete(@PathVariable final String slug,
                                 @PathVariable final Long id,
                                 @AuthenticationPrincipal final LoginUser loginUser) {
        commentService.delete(slug, id, loginUser.getUser().getId());

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{slug}/comments")
    public ResponseEntity<CommentListResponseDto> get(@PathVariable final String slug) throws JsonProcessingException {
        final CommentListResponseDto commentList = new CommentListResponseDto(commentService.getAll(slug));

        return ResponseEntity.status(HttpStatus.CREATED).body(commentList);
    }
}