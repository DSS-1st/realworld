package com.dss.realworld.article.api;

import com.dss.realworld.article.api.dto.ArticleListResponseDto;
import com.dss.realworld.article.api.dto.ArticleResponseDto;
import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.api.dto.UpdateArticleRequestDto;
import com.dss.realworld.article.app.ArticleService;
import com.dss.realworld.common.auth.LoginUser;
import com.dss.realworld.common.error.CustomExceptionHandler;
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
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<ArticleListResponseDto> list(@RequestParam(value = "tag", required = false) final String tag,
                                                       @RequestParam(value = "author", required = false) final String author,
                                                       @RequestParam(value = "favorited", required = false) final String favorited,
                                                       @RequestParam(required = false, defaultValue = "0") final int offset,
                                                       @RequestParam(required = false, defaultValue = "20") final int limit,
                                                       @AuthenticationPrincipal final LoginUser loginUser) {
        ArticleListResponseDto articles;
        if (loginUser == null) articles = articleService.list(tag, author, favorited, null, limit, offset);
        else articles = articleService.list(tag, author, favorited, loginUser.getUser().getId(), limit, offset);

        return ResponseEntity.status(HttpStatus.CREATED).body(articles);
    }

    @GetMapping(value = "/{slug}")
    public ResponseEntity<ArticleResponseDto> get(@PathVariable final String slug,
                                                  @AuthenticationPrincipal final LoginUser loginUser) {
        ArticleResponseDto articleResponseDto;
        if (loginUser == null) articleResponseDto = articleService.get(slug, null);
        else articleResponseDto = articleService.get(slug, loginUser.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponseDto);
    }

    @GetMapping(value = "/feed")
    public ResponseEntity<ArticleListResponseDto> feed(@RequestParam(required = false, defaultValue = "0") final int offset,
                                                       @RequestParam(required = false, defaultValue = "20") final int limit,
                                                       @AuthenticationPrincipal final LoginUser loginUser) {
        ArticleListResponseDto articles = articleService.feed(loginUser.getUser().getId(), limit, offset);

        return ResponseEntity.status(HttpStatus.CREATED).body(articles);
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDto> create(@RequestBody @Valid final CreateArticleRequestDto createArticleRequestDto,
                                                     final BindingResult bindingResult,
                                                     @AuthenticationPrincipal final LoginUser loginUser) {
        CustomExceptionHandler.checkOrThrow(bindingResult);

        ArticleResponseDto articleResponseDto = articleService.save(createArticleRequestDto, loginUser.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponseDto);
    }

    @PutMapping(value = "/{slug}")
    public ResponseEntity<ArticleResponseDto> update(@RequestBody @Valid final UpdateArticleRequestDto updateArticleRequestDto,
                                                     final BindingResult bindingResult,
                                                     @PathVariable final String slug,
                                                     @AuthenticationPrincipal final LoginUser loginUser) {
        CustomExceptionHandler.checkOrThrow(bindingResult);

        ArticleResponseDto articleResponseDto = articleService.update(updateArticleRequestDto, loginUser.getUser().getId(), slug);

        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponseDto);
    }

    @DeleteMapping(value = "/{slug}")
    public ResponseEntity delete(@PathVariable final String slug,
                                 @AuthenticationPrincipal final LoginUser loginUser) {
        articleService.delete(slug, loginUser.getUser().getId());

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{slug}/favorite")
    public ResponseEntity<ArticleResponseDto> favorite(@PathVariable final String slug,
                                                       @AuthenticationPrincipal final LoginUser loginUser) {
        ArticleResponseDto articleResponseDto = articleService.favorite(slug, loginUser.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponseDto);
    }

    @DeleteMapping(value = "/{slug}/favorite")
    public ResponseEntity<ArticleResponseDto> unfavorite(@PathVariable final String slug,
                                                         @AuthenticationPrincipal final LoginUser loginUser) {
        ArticleResponseDto articleResponseDto = articleService.unfavorite(slug, loginUser.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponseDto);
    }
}