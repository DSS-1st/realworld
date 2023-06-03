package com.dss.realworld.article.api;

import com.dss.realworld.article.api.dto.ArticleListResponseDto;
import com.dss.realworld.article.api.dto.ArticleResponseDto;
import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.api.dto.UpdateArticleRequestDto;
import com.dss.realworld.article.app.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/api/articles")
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<ArticleListResponseDto> list(@RequestParam(value = "tag", required = false) String tag,
                                                       @RequestParam(value = "author", required = false) String author,
                                                       @RequestParam(value = "favorited", required = false) String favorited,
                                                       @RequestParam(required = false, defaultValue = "0") final int offset,
                                                       @RequestParam(required = false, defaultValue = "20") final int limit) {
        ArticleListResponseDto articles = articleService.list(tag, author, favorited, getLoginUserId(), limit, offset);

        return ResponseEntity.ok(articles);
    }

    @GetMapping(value = "/{slug}")
    public ResponseEntity<ArticleResponseDto> findBySlug(@PathVariable final String slug) {
        ArticleResponseDto articleResponseDto = articleService.findBySlug(slug, getLoginUserId());

        return ResponseEntity.ok(articleResponseDto);
    }

    @GetMapping(value = "/feed")
    public ResponseEntity<ArticleListResponseDto> feed(@RequestParam(required = false, defaultValue = "0") final int offset,
                                                       @RequestParam(required = false, defaultValue = "20") final int limit) {
        ArticleListResponseDto articles = articleService.feed(getLoginUserId(), limit, offset);

        return ResponseEntity.ok(articles);
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDto> create(@RequestBody @Valid CreateArticleRequestDto createArticleRequestDto, BindingResult bindingResult) {
        ArticleResponseDto articleResponseDto = articleService.save(createArticleRequestDto, getLoginUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponseDto);
    }

    @PutMapping(value = "/{slug}")
    public ResponseEntity<ArticleResponseDto> update(@RequestBody @Valid final UpdateArticleRequestDto updateArticleRequestDto, BindingResult bindingResult,
                                                     @PathVariable final String slug) {
        ArticleResponseDto articleResponseDto = articleService.update(updateArticleRequestDto, getLoginUserId(), slug);

        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponseDto);
    }

    @DeleteMapping(value = "/{slug}")
    public ResponseEntity delete(@PathVariable final String slug) {
        articleService.delete(slug, getLoginUserId());

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{slug}/favorite")
    public ResponseEntity<ArticleResponseDto> favorite(@PathVariable final String slug) {
        ArticleResponseDto articleResponseDto = articleService.favorite(slug, getLoginUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponseDto);
    }

    @DeleteMapping(value = "/{slug}/favorite")
    public ResponseEntity<ArticleResponseDto> unfavorite(@PathVariable final String slug) {
        ArticleResponseDto articleResponseDto = articleService.unfavorite(slug, getLoginUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponseDto);
    }

    // todo SecurityContextHolder에서 인증 정보 얻기
    private Long getLoginUserId() {
        return 1L;
    }
}