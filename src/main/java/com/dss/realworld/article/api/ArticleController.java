package com.dss.realworld.article.api;

import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.api.dto.CreateArticleResponseDto;
import com.dss.realworld.article.app.ArticleService;
import com.dss.realworld.article.domain.dto.GetArticleDto;
import com.dss.realworld.user.domain.repository.GetUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/articles")
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public CreateArticleResponseDto create(@RequestBody CreateArticleRequestDto createArticleRequestDto) {
        GetArticleDto getArticleDto = articleService.create(createArticleRequestDto, getLogonUserId());

        GetUserDto getUserDto = articleService.getAuthor(getArticleDto.getUserId());

        return new CreateArticleResponseDto(getArticleDto, getUserDto);
    }

    @DeleteMapping("{slug}")
    public void delete(@PathVariable String slug) {
        articleService.delete(slug, getLogonUserId());
    }

    // todo SecurityContextHolder에서 인증 정보 얻기
    private Long getLogonUserId(){
        return 1L;
    }
}
