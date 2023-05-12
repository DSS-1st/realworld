package com.dss.realworld.article.app;

import com.dss.realworld.article.api.dto.ArticleContentDto;
import com.dss.realworld.article.api.dto.ArticleResponseDto;
import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.api.dto.UpdateArticleRequestDto;
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
        Article foundArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        ArticleContentDto content = ArticleContentDto.of(foundArticle);
        AuthorDto author = getAuthor(foundArticle.getUserId());

        return new ArticleResponseDto(content, author);
    }

    @Override
    @Transactional
    public ArticleResponseDto save(CreateArticleRequestDto createArticleRequestDto, Long loginUserId) {
        Long maxId = articleRepository.findMaxId().orElse(0L);
        Article article = createArticleRequestDto.convert(loginUserId, maxId);
        articleRepository.persist(article);

        return getArticleResponseDto(article);
    }

    private ArticleResponseDto getArticleResponseDto(final Article newArticle) {
        Article foundArticle = articleRepository.findById(newArticle.getId()).orElseThrow(ArticleNotFoundException::new);

        ArticleContentDto content = ArticleContentDto.of(foundArticle);
        AuthorDto author = getAuthor(foundArticle.getUserId());

        return new ArticleResponseDto(content, author);
    }

    @Override
    @Transactional
    public ArticleResponseDto update(final UpdateArticleRequestDto updateArticleRequestDto, final Long loginUserId, final String slug) {
        Article savedArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        if(savedArticle.isAuthorMatch(loginUserId)) throw new ArticleAuthorNotMatchException();

        Article toBeUpdatedArticle = savedArticle.updateArticle(updateArticleRequestDto);
        articleRepository.update(toBeUpdatedArticle);

        return getArticleResponseDto(toBeUpdatedArticle);
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