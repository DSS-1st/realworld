package com.dss.realworld.article.app;

import com.dss.realworld.article.api.CreateArticleRequestDto;
import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.repository.ArticleRepository;
import com.dss.realworld.article.repository.GetArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public GetArticleDto createArticle(CreateArticleRequestDto createArticleRequestDto) {
        Article article = Article.builder()
                .title(createArticleRequestDto.getArticle().getTitle())
                .description(createArticleRequestDto.getArticle().getDescription())
                .body(createArticleRequestDto.getArticle().getBody())
                .build();
        articleRepository.createArticle(article);
        return articleRepository.getArticlebySlug(article.getSlug()); //todo 저장/로딩 로직 구현필
    }
}
