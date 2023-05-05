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
    public GetArticleDto create(CreateArticleRequestDto createArticleRequestDto, Long logonUserId) {
        Long maxId = articleRepository.getMaxId();

        if (maxId == null) {
            maxId = 0L;
        }

        Article article = createArticleRequestDto.convertToArticle(logonUserId, maxId);
        articleRepository.create(article);

        return articleRepository.getById(article.getId());
    }

    @Override
    @Transactional
    public void delete(String slug, Long userId) {
        Optional<GetArticleDto> foundArticle = articleRepository.getBySlug(slug);
        foundArticle.orElseThrow(ArticleNotFoundException::new);

        if (foundArticle.get().isAuthorMatch(userId)) throw new ArticleAuthorNotMatchException();

        articleRepository.delete(foundArticle.get().getId());
    }

    private boolean isEmpty(final Optional<GetArticleDto> foundArticle) {
        return foundArticle.isEmpty();
    }

    @Override
    public GetUserDto getAuthor(Long userId) {
        return userRepository.getById(userId);
    }
}