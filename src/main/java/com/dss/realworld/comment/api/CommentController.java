package com.dss.realworld.comment.api;

import com.dss.realworld.comment.api.dto.AddCommentRequestDto;
import com.dss.realworld.comment.api.dto.AddCommentResponseDto;
import com.dss.realworld.comment.api.dto.CommentDto;
import com.dss.realworld.comment.app.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/articles")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/{slug}/comments")
    public ResponseEntity<AddCommentResponseDto> create(@RequestBody AddCommentRequestDto addCommentRequestDto,
                                    @PathVariable String slug) {
        AddCommentResponseDto addCommentResponseDto = commentService.add(addCommentRequestDto, getLogonUserId(), slug);

        return new ResponseEntity<>(addCommentResponseDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "{slug}/comments/{id}")
    public void delete(@PathVariable String slug, @PathVariable Long id) {
        commentService.delete(slug, id, getLogonUserId());
    }

    @GetMapping(value = "/{slug}/comments")
    public ResponseEntity<String> get(@PathVariable String slug) throws JsonProcessingException {
        final List<CommentDto> commentList = commentService.getAll(slug);

        return new ResponseEntity<>(getResponseBody(commentList), HttpStatus.OK);
    }

    private String getResponseBody(final List<CommentDto> comments) throws JsonProcessingException {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .writerWithDefaultPrettyPrinter()
                .withRootName("comments")
                .writeValueAsString(comments);
    }

    // todo SecurityContextHolder에서 인증 정보 얻기
    private Long getLogonUserId() {
        return 1L;
    }
}