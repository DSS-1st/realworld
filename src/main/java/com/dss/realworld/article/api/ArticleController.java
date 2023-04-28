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
    public CreateArticleResponseDto createArticle(@RequestBody CreateArticleRequestDto createArticleRequestDto) {
        GetArticleDto getArticleDto = articleService.createArticle(createArticleRequestDto);

        GetUserDto getUserDto = articleService.getArticleAuthor(getArticleDto.getUserId());

        return new CreateArticleResponseDto(getArticleDto, getUserDto);
    }

    @DeleteMapping("{slug}")
    public void deleteArticle(@PathVariable String slug) {
        articleService.deleteArticle(slug, getUserId());
    }

    // todo JWT을 통해 username 얻어오기
    private Long getUserId(){
        return null;
    }
}
