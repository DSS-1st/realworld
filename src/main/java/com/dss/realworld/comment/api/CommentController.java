package com.dss.realworld.comment.api;

import com.dss.realworld.comment.api.dto.AddCommentRequestDto;
import com.dss.realworld.comment.api.dto.AddCommentResponseDto;
import com.dss.realworld.comment.api.dto.CommentListResponseDto;
import com.dss.realworld.comment.app.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/api/articles")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/{slug}/comments")
    public ResponseEntity<AddCommentResponseDto> create(@RequestBody @Valid AddCommentRequestDto addCommentRequestDto, BindingResult bindingResult,
                                                        @PathVariable String slug) {
        AddCommentResponseDto addCommentResponseDto = commentService.add(addCommentRequestDto, getLogonUserId(), slug);

        return ResponseEntity.ok(addCommentResponseDto);
    }

    @DeleteMapping(value = "{slug}/comments/{id}")
    public ResponseEntity delete(@PathVariable String slug, @PathVariable Long id) {
        commentService.delete(slug, id, getLogonUserId());

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{slug}/comments")
    public ResponseEntity<CommentListResponseDto> get(@PathVariable String slug) throws JsonProcessingException {
        final CommentListResponseDto commentList = new CommentListResponseDto(commentService.getAll(slug));

        return ResponseEntity.ok(commentList);
    }

    // todo SecurityContextHolder에서 인증 정보 얻기
    private Long getLogonUserId() {
        return 1L;
    }
}