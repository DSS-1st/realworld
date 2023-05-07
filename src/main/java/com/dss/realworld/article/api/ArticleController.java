package com.dss.realworld.article.api;

import com.dss.realworld.common.dto.AuthorDto;
import com.dss.realworld.article.api.dto.ArticleContentDto;
import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.api.dto.CreateArticleResponseDto;
import com.dss.realworld.article.app.ArticleService;
import com.dss.realworld.article.domain.dto.GetArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/articles")
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public CreateArticleResponseDto create(@RequestBody CreateArticleRequestDto createArticleRequestDto) {
        GetArticleDto article = articleService.save(createArticleRequestDto, getLogonUserId());
        ArticleContentDto content = ArticleContentDto.of(article.getSlug(), article.getTitle(), article.getDescription(), article.getBody(), article.getCreatedAt(), article.getUpdatedAt());
        AuthorDto author = articleService.getAuthor(article.getUserId());

        return new CreateArticleResponseDto(content, author);
    }

    @DeleteMapping(value = "{slug}")
    public void delete(@PathVariable String slug) {
        articleService.delete(slug, getLogonUserId());
    }

    // todo SecurityContextHolder에서 인증 정보 얻기
    private Long getLogonUserId() {
        return 1L;
    }
}
