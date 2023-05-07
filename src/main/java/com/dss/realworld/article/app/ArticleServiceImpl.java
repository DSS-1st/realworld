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

        if (maxId == null) maxId = 0L;

        Article article = createArticleRequestDto.convert(logonUserId, maxId);
        articleRepository.persist(article);

        return articleRepository.getById(article.getId());
    }

    @Override
    @Transactional
    public void delete(String slug, Long userId) {
        GetArticleDto foundArticle = articleRepository.getBySlug(slug).orElseThrow(ArticleNotFoundException::new);

        assert !foundArticle.isAuthorMatch(userId) : new ArticleAuthorNotMatchException();

        articleRepository.delete(foundArticle.getId());
    }

    @Override
    public GetUserDto getAuthor(Long userId) {
        return userRepository.getById(userId);
    }
}