package com.dss.realworld.article.app;

import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.dto.GetArticleDto;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.error.exception.ArticleAuthorNotMatchException;
import com.dss.realworld.error.exception.ArticleNotFoundException;
import com.dss.realworld.user.domain.repository.GetUserDto;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public GetArticleDto createArticle(CreateArticleRequestDto createArticleRequestDto, Long logonUserId) {
        Long maxArticleId = articleRepository.getMaxArticleId();

        if (maxArticleId == null) {
            maxArticleId = 0L;
        }

        Article article = createArticleRequestDto.convertToArticle(logonUserId, maxArticleId);
        articleRepository.createArticle(article);

        return articleRepository.getArticleById(article.getId());
    }

    @Override
    public void deleteArticle(String slug, Long userId) {
        GetArticleDto foundArticle = articleRepository.getArticleBySlug(slug);
        if (Optional.ofNullable(foundArticle).isEmpty()) {
            throw new ArticleNotFoundException();
        }

        if (foundArticle.getUserId().compareTo(userId) != 0) {
            throw new ArticleAuthorNotMatchException();
        }

        articleRepository.deleteArticle(foundArticle.getId());
    }

    @Override
    public GetUserDto getArticleAuthor(Long userId) {
        return userRepository.getUserById(userId);
    }
}