package com.dss.realworld.comment.app;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.comment.api.dto.AddCommentRequestDto;
import com.dss.realworld.comment.api.dto.AddCommentResponseDto;
import com.dss.realworld.comment.api.dto.CommentDto;
import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.comment.domain.repository.CommentRepository;
import com.dss.realworld.common.dto.AuthorDto;
import com.dss.realworld.common.error.exception.ArticleNotFoundException;
import com.dss.realworld.common.error.exception.CommentNotFoundException;
import com.dss.realworld.common.error.exception.UserNotFoundException;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.FollowingRepository;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final FollowingRepository followingRepository;

    @Override
    @Transactional
    public AddCommentResponseDto add(final AddCommentRequestDto addCommentRequestDto, final Long loginId, final String slug) {
        Article foundArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);

        Comment comment = Comment.builder()
                .body(addCommentRequestDto.getBody())
                .articleId(foundArticle.getId())
                .userId(loginId)
                .build();

        commentRepository.persist(comment);
        Comment foundComment = commentRepository.findById(comment.getId()).orElseThrow(CommentNotFoundException::new);
        AuthorDto author = getAuthor(foundComment.getUserId());

        return new AddCommentResponseDto(foundComment, author);
    }

    @Override
    public AuthorDto getAuthor(final Long userId) {
        User foundAuthor = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return AuthorDto.of(foundAuthor.getUsername(), foundAuthor.getBio(), foundAuthor.getImage(), false);
    }

    @Override
    public List<CommentDto> getAll(final String slug) {
        Article foundArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);

        return commentRepository.findAll(foundArticle.getId())
                .stream()
                .map(comment -> CommentDto.of(comment, getAuthor(comment.getUserId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getAll(final String slug, final Long loginId) {
        Article foundArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);

        return commentRepository.findAll(foundArticle.getId())
                .stream()
                .map(comment -> CommentDto.of(comment, getAuthor(comment.getUserId(), loginId)))
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDto getAuthor(final Long userId, final Long loginId) {
        User foundAuthor = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        int result = followingRepository.isFollowing(foundAuthor.getId(), loginId);

        return AuthorDto.of(foundAuthor.getUsername(), foundAuthor.getBio(), foundAuthor.getImage(), result == 1);
    }

    @Override
    @Transactional
    public int delete(final String slug, final Long commentId, final Long userId) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);

        return commentRepository.delete(commentId, article.getId(), userId);
    }
}