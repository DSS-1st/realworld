package com.dss.realworld.article.app;

import com.dss.realworld.article.api.dto.ArticleContentDto;
import com.dss.realworld.article.api.dto.ArticleResponseDto;
import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.common.dto.AuthorDto;
import com.dss.realworld.error.exception.ArticleAuthorNotMatchException;
import com.dss.realworld.error.exception.ArticleNotFoundException;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Override
    public ArticleResponseDto findBySlug(String slug) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        ArticleContentDto content = ArticleContentDto.of(article.getSlug(), article.getTitle(), article.getDescription(), article.getBody(), article.getCreatedAt(), article.getUpdatedAt());
        AuthorDto author = getAuthor(article.getUserId());

        return new ArticleResponseDto(content, author);
    }

    @Override
    @Transactional
    public ArticleResponseDto save(CreateArticleRequestDto createArticleRequestDto, Long logonUserId) {
        Long maxId = articleRepository.findMaxId().orElse(0L);
        Article article = createArticleRequestDto.convert(logonUserId, maxId);
        articleRepository.persist(article);

        Article savedArticle = articleRepository.findById(article.getId()).orElseThrow(ArticleNotFoundException::new);
        ArticleContentDto content = ArticleContentDto.of(savedArticle.getSlug(), savedArticle.getTitle(), savedArticle.getDescription(), savedArticle.getBody(), savedArticle.getCreatedAt(), savedArticle.getUpdatedAt());
        AuthorDto author = getAuthor(savedArticle.getUserId());

        return new ArticleResponseDto(content, author);
    }

    @Override
    @Transactional
    public void delete(String slug, Long loginId) {
        Article foundArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        if (foundArticle.isAuthorMatch(loginId)) throw new ArticleAuthorNotMatchException();

        articleRepository.delete(foundArticle.getId());
    }

    @Override
    public AuthorDto getAuthor(Long userId) {
        User foundUser = userRepository.findById(userId);

        return AuthorDto.of(foundUser.getUsername(), foundUser.getBio(), foundUser.getImage());
    }
}