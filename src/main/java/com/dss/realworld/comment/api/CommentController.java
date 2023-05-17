package com.dss.realworld.comment.api;

import com.dss.realworld.comment.api.dto.AddCommentRequestDto;
import com.dss.realworld.comment.api.dto.AddCommentResponseDto;
import com.dss.realworld.comment.api.dto.GetCommentsResponseDto;
import com.dss.realworld.comment.app.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/articles")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/{slug}/comments")
    public ResponseEntity<?> create(@RequestBody AddCommentRequestDto addCommentRequestDto,
                                            @PathVariable String slug) {

        AddCommentResponseDto addCommentResponseDto = commentService.add(addCommentRequestDto, getLogonUserId(), slug);
        return new ResponseEntity<>(addCommentResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("{slug}/comments/{id}")
    public void delete(@PathVariable String slug,
                              @PathVariable Long id) {
        commentService.deleteComment(slug,id,getLogonUserId());
    }

    @GetMapping("/{slug}/comments")
    public ResponseEntity<?> get(@PathVariable String slug) {
        final GetCommentsResponseDto getCommentsResponseDto = commentService.getAll(slug);


//        List<GetCommentsResponseDto.CommentDto> comments = objects;
// comments 리스트에 CommentDto 객체들을 추가

//        GetCommentsResponseDto responseDto = new GetCommentsResponseDto(comments);


        return new ResponseEntity(getCommentsResponseDto, HttpStatus.OK);
    }

    // todo SecurityContextHolder에서 인증 정보 얻기
    private Long getLogonUserId(){
        return 1L;
    }
}