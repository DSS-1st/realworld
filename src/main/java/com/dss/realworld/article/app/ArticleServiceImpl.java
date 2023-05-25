package com.dss.realworld.article.app;

import com.dss.realworld.article.api.dto.ArticleContentDto;
import com.dss.realworld.article.api.dto.ArticleResponseDto;
import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.api.dto.UpdateArticleRequestDto;
import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.ArticleTag;
import com.dss.realworld.article.domain.ArticleUsers;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.article.domain.repository.ArticleTagRepository;
import com.dss.realworld.article.domain.repository.ArticleUsersRepository;
import com.dss.realworld.comment.domain.repository.CommentRepository;
import com.dss.realworld.common.dto.AuthorDto;
import com.dss.realworld.error.exception.ArticleAuthorNotMatchException;
import com.dss.realworld.error.exception.ArticleNotFoundException;
import com.dss.realworld.error.exception.CustomApiException;
import com.dss.realworld.error.exception.UserNotFoundException;
import com.dss.realworld.tag.domain.Tag;
import com.dss.realworld.tag.domain.repository.TagRepository;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;
    private final ArticleUsersRepository articleUsersRepository;
    private final CommentRepository commentRepository;

    @Override
    public ArticleResponseDto findBySlug(String slug, Long loginId) {
        Article foundArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        List<String> tagList = articleTagRepository.findTagsByArticleId(foundArticle.getId());
        ArticleContentDto content = ArticleContentDto.of(foundArticle);
        AuthorDto author = getAuthor(foundArticle.getUserId());

        boolean favorited = isFavorite(loginId, foundArticle);
        int favoritesCount = articleUsersRepository.findCountByArticleId(foundArticle.getId());

        return new ArticleResponseDto(content, author, tagList, favorited, favoritesCount);
    }

    @Override
    @Transactional
    public ArticleResponseDto favorite(final String slug, Long loginId) {
        Article foundArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        List<String> tagList = articleTagRepository.findTagsByArticleId(foundArticle.getId());
        ArticleContentDto content = ArticleContentDto.of(foundArticle);
        AuthorDto author = getAuthor(foundArticle.getUserId());

        try {
            articleUsersRepository.persist(new ArticleUsers(foundArticle.getId(), loginId));
        } catch (DuplicateKeyException e) {
            throw new CustomApiException("이미 좋아요한 글입니다.");
        }
        boolean favorited = isFavorite(loginId, foundArticle);
        int favoritesCount = articleUsersRepository.findCountByArticleId(foundArticle.getId());

        return new ArticleResponseDto(content, author, tagList, favorited, favoritesCount);
    }

    private boolean isFavorite(final Long loginId, final Article foundArticle) {
        return (loginId != null) && (articleUsersRepository.isFavorite(foundArticle.getId(), loginId) == 1);
    }

    @Override
    @Transactional
    public ArticleResponseDto unfavorite(final String slug, Long loginId) {
        Article foundArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        List<String> tagList = articleTagRepository.findTagsByArticleId(foundArticle.getId());
        ArticleContentDto content = ArticleContentDto.of(foundArticle);
        AuthorDto author = getAuthor(foundArticle.getUserId());

        articleUsersRepository.delete(foundArticle.getId(), loginId);
        int favoritesCount = articleUsersRepository.findCountByArticleId(foundArticle.getId());

        return new ArticleResponseDto(content, author, tagList, false, favoritesCount);
    }

    @Override
    @Transactional
    public ArticleResponseDto save(CreateArticleRequestDto createArticleRequestDto, Long loginUserId) {
        Long maxId = articleRepository.findMaxId().orElse(0L);
        Article article = createArticleRequestDto.convert(loginUserId, maxId);
        articleRepository.persist(article);

        Set<Tag> tags = getTagSet(createArticleRequestDto);
        if (!tags.isEmpty()) saveTags(article, tags);

        return getArticleResponseDto(article);
    }

    private static Set<Tag> getTagSet(final CreateArticleRequestDto createArticleRequestDto) {
        return createArticleRequestDto.getTagList().stream()
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    private void saveTags(final Article article, final Set<Tag> tags) {
        for (Tag tag : tags) {
            try {
                tagRepository.persist(tag);
                articleTagRepository.persist(new ArticleTag(article.getId(), tag.getId()));
            } catch (DuplicateKeyException e) {
                Long existentId = tagRepository.findIdByName(tag.getName());
                articleTagRepository.persist(new ArticleTag(article.getId(), existentId));
            }
        }
    }

    private ArticleResponseDto getArticleResponseDto(final Article article) {
        Article foundArticle = articleRepository.findById(article.getId()).orElseThrow(ArticleNotFoundException::new);
        List<String> tagList = articleTagRepository.findTagsByArticleId(foundArticle.getId());
        ArticleContentDto content = ArticleContentDto.of(foundArticle);
        AuthorDto author = getAuthor(foundArticle.getUserId());

        return new ArticleResponseDto(content, author, tagList);
    }

    @Override
    @Transactional
    public ArticleResponseDto update(final UpdateArticleRequestDto updateArticleRequestDto, final Long loginUserId, final String slug) {
        Article savedArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        if (savedArticle.isAuthorMatch(loginUserId)) throw new ArticleAuthorNotMatchException();

        Article toBeUpdatedArticle = savedArticle.updateArticle(updateArticleRequestDto);
        articleRepository.update(toBeUpdatedArticle);

        return getArticleResponseDto(toBeUpdatedArticle);
    }

    @Override
    @Transactional
    public void delete(String slug, Long loginId) {
        Article foundArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        if (foundArticle.isAuthorMatch(loginId)) throw new ArticleAuthorNotMatchException();

        articleTagRepository.deleteByArticleId(foundArticle.getId());
        commentRepository.deleteByArticleId(foundArticle.getId());
        articleUsersRepository.deleteArticleRelation(foundArticle.getId());
        articleRepository.delete(foundArticle.getId());
    }

    @Override
    public AuthorDto getAuthor(Long userId) {
        User foundUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return AuthorDto.of(foundUser.getUsername(), foundUser.getBio(), foundUser.getImage());
    }
}