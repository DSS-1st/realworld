package com.dss.realworld.article.app;

import com.dss.realworld.article.api.dto.*;
import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.ArticleTag;
import com.dss.realworld.article.domain.ArticleUsers;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.article.domain.repository.ArticleTagRepository;
import com.dss.realworld.article.domain.repository.ArticleUsersRepository;
import com.dss.realworld.comment.domain.repository.CommentRepository;
import com.dss.realworld.common.dto.AuthorDto;
import com.dss.realworld.common.error.exception.*;
import com.dss.realworld.tag.domain.Tag;
import com.dss.realworld.tag.domain.repository.TagRepository;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.FollowingRepository;
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
    private final FollowingRepository followingRepository;

    @Override
    public ArticleListResponseDto list(final String tag, final String author, final String favorited, final Long loginId, int limit, int offset) {
        List<Article> foundArticles = articleRepository.list(tag, author, favorited, limit, offset);
        if (foundArticles.isEmpty()) return new ArticleListResponseDto(0);

        List<ArticleListItemResponseDto> articles = foundArticles.stream()
                .map(article -> getArticleListItemResponseDto(loginId, article))
                .collect(Collectors.toList());

        return new ArticleListResponseDto(articles);
    }

    private ArticleListItemResponseDto getArticleListItemResponseDto(final Long loginId, final Article article) {
        return new ArticleListItemResponseDto(bindArticleDto(loginId, article));
    }

    private ArticleDtoBinder bindArticleDto(final Long loginId, final Article article) {
        List<String> tagList = articleTagRepository.findTagsByArticleId(article.getId());
        ArticleContentDto content = ArticleContentDto.of(article);
        AuthorDto author = getAuthor(article.getUserId(), loginId);

        boolean favorited = isFavorite(loginId, article);
        int favoritesCount = articleUsersRepository.findCountByArticleId(article.getId());

        return new ArticleDtoBinder(content, author, tagList, favorited, favoritesCount);
    }

    @Override
    public AuthorDto getAuthor(final Long userId, final Long loginId) {
        User foundUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        boolean followed = isFollowing(foundUser, loginId);

        return AuthorDto.of(foundUser.getUsername(), foundUser.getBio(), foundUser.getImage(), followed);
    }

    private boolean isFollowing(final User foundUser, final Long loginId) {
        return (loginId != null) && (followingRepository.isFollowing(foundUser.getId(), loginId) == 1);
    }

    @Override
    public ArticleListResponseDto feed(final Long loginId, int limit, int offset) {
        List<Article> followedArticles = articleRepository.findArticleByFollower(loginId, limit, offset);
        if (followedArticles.isEmpty()) return new ArticleListResponseDto(0);

        List<ArticleListItemResponseDto> articles = followedArticles.stream()
                .map(article -> getArticleListItemResponseDto(loginId, article))
                .collect(Collectors.toList());

        return new ArticleListResponseDto(articles);
    }

    @Override
    @Transactional
    public ArticleResponseDto favorite(final String slug, final Long loginId) {
        Article foundArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        List<String> tagList = articleTagRepository.findTagsByArticleId(foundArticle.getId());
        ArticleContentDto content = ArticleContentDto.of(foundArticle);
        AuthorDto author = getAuthor(foundArticle.getUserId(), loginId);

        try {
            articleUsersRepository.persist(new ArticleUsers(foundArticle.getId(), loginId));
        } catch (DuplicateKeyException e) {
            throw new DuplicateFavoriteException();
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
    public ArticleResponseDto unfavorite(final String slug, final Long loginId) {
        Article foundArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        List<String> tagList = articleTagRepository.findTagsByArticleId(foundArticle.getId());
        ArticleContentDto content = ArticleContentDto.of(foundArticle);
        AuthorDto author = getAuthor(foundArticle.getUserId(), loginId);

        articleUsersRepository.delete(foundArticle.getId(), loginId);
        int favoritesCount = articleUsersRepository.findCountByArticleId(foundArticle.getId());

        return new ArticleResponseDto(content, author, tagList, false, favoritesCount);
    }

    @Override
    public ArticleResponseDto get(final String slug, final Long loginId) {
        Article foundArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);

        return getArticleResponseDto(loginId, foundArticle);
    }

    private ArticleResponseDto getArticleResponseDto(final Long loginId, final Article article) {
        return new ArticleResponseDto(bindArticleDto(loginId, article));
    }

    @Override
    @Transactional
    public ArticleResponseDto save(final CreateArticleRequestDto createArticleRequestDto, final Long loginId) {
        Long maxId = articleRepository.findMaxId().orElse(0L);
        Article article = createArticleRequestDto.convert(loginId, maxId + 1);
        articleRepository.persist(article);

        Set<Tag> tags = getTagSet(createArticleRequestDto);
        if (!tags.isEmpty()) saveTags(article, tags);

        return getArticleResponseDto(article, loginId);
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
                Long existentId = tagRepository.findIdByName(tag.getName()).orElseThrow(TagNotFoundException::new);
                articleTagRepository.persist(new ArticleTag(article.getId(), existentId));
            }
        }
    }

    private ArticleResponseDto getArticleResponseDto(final Article article, final Long loginId) {
        Article foundArticle = articleRepository.findById(article.getId()).orElseThrow(ArticleNotFoundException::new);
        List<String> tagList = articleTagRepository.findTagsByArticleId(foundArticle.getId());
        ArticleContentDto content = ArticleContentDto.of(foundArticle);
        AuthorDto author = getAuthor(foundArticle.getUserId(), loginId);

        return new ArticleResponseDto(content, author, tagList);
    }

    @Override
    @Transactional
    public ArticleResponseDto update(final UpdateArticleRequestDto updateArticleRequestDto, final Long loginId, final String slug) {
        Article savedArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        if (savedArticle.isAuthorMatch(loginId)) throw new ArticleAuthorNotMatchException();

        Article toBeUpdatedArticle = savedArticle.updateArticle(updateArticleRequestDto);
        articleRepository.update(toBeUpdatedArticle);

        return getArticleResponseDto(toBeUpdatedArticle, loginId);
    }

    @Override
    @Transactional
    public void delete(final String slug, final Long loginId) {
        Article foundArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        if (foundArticle.isAuthorMatch(loginId)) throw new ArticleAuthorNotMatchException();

        articleTagRepository.deleteByArticleId(foundArticle.getId());
        commentRepository.deleteByArticleId(foundArticle.getId());
        articleUsersRepository.deleteArticleRelation(foundArticle.getId());
        articleRepository.delete(foundArticle.getId());
    }
}