package com.dss.realworld.comment.app;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.comment.api.dto.AddCommentRequestDto;
import com.dss.realworld.comment.api.dto.AddCommentResponseDto;
import com.dss.realworld.comment.api.dto.CommentDto;
import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.comment.domain.repository.CommentRepository;
import com.dss.realworld.common.dto.AuthorDto;
import com.dss.realworld.error.exception.ArticleNotFoundException;
import com.dss.realworld.error.exception.CommentNotFoundException;
import com.dss.realworld.error.exception.UserNotFoundException;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.FollowRelationRepository;
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
    private final FollowRelationRepository followRelationRepository;

    @Override
    @Transactional
    public AddCommentResponseDto add(AddCommentRequestDto addCommentRequestDto, Long loginId, String slug) {
        Article foundArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);

        Comment comment = Comment.builder()
                .body(addCommentRequestDto.getBody())
                .articleId(foundArticle.getId())
                .userId(loginId)
                .build();

        commentRepository.persist(comment);
        Comment foundComment = commentRepository.findById(comment.getId()).orElseThrow(CommentNotFoundException::new);
        AuthorDto author = getAuthor(comment.getUserId());

        return new AddCommentResponseDto(foundComment, author);
    }

    @Override
    @Transactional
    public int delete(final String slug, final Long commentId, Long userId) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);

        return commentRepository.delete(commentId, article.getId(), userId);
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
    public AuthorDto getAuthor(Long userId) {
        User foundAuthor = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        int result = followRelationRepository.isFollowing(foundAuthor.getId(), userId);

        return AuthorDto.of(foundAuthor.getUsername(), foundAuthor.getBio(), foundAuthor.getImage(), result == 1);
    }
}