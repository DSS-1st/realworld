package com.dss.realworld.article.api;

import com.dss.realworld.article.api.dto.ArticleResponseDto;
import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.app.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/articles")
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping(value = "/{slug}")
    public ResponseEntity<?> findBySlug(@PathVariable final String slug) {
        ArticleResponseDto articleResponseDto = articleService.findBySlug(slug);

        return new ResponseEntity<>(articleResponseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateArticleRequestDto createArticleRequestDto) {
        ArticleResponseDto articleResponseDto = articleService.save(createArticleRequestDto, getLogonUserId());

        return new ResponseEntity<>(articleResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{slug}")
    public ResponseEntity<?> delete(@PathVariable String slug) {
        articleService.delete(slug, getLogonUserId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // todo SecurityContextHolder에서 인증 정보 얻기
    private Long getLogonUserId() {
        return 1L;
    }
}