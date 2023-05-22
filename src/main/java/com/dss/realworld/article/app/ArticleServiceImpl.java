package com.dss.realworld.article.app;

import com.dss.realworld.article.api.dto.ArticleContentDto;
import com.dss.realworld.article.api.dto.ArticleResponseDto;
import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.api.dto.UpdateArticleRequestDto;
import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.ArticleTag;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.article.domain.repository.ArticleTagRepository;
import com.dss.realworld.common.dto.AuthorDto;
import com.dss.realworld.error.exception.ArticleAuthorNotMatchException;
import com.dss.realworld.error.exception.ArticleNotFoundException;
import com.dss.realworld.tag.domain.Tag;
import com.dss.realworld.tag.domain.repository.TagRepository;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;

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

        Set<Tag> tags = createArticleRequestDto.getTags();
        if (tags != null) saveTags(article, tags);

        return getArticleResponseDto(article);
    }

    private void saveTags(final Article article, final Set<Tag> tags) {
        for (Tag tag : tags) {
            tagRepository.persist(tag);
            articleTagRepository.persist(new ArticleTag(article.getId(), tag.getId()));
        }
    }

    // todo 저장된 tag 불러와서 dto만들기
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