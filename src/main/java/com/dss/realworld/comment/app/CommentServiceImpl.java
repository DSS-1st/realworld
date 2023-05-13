package com.dss.realworld.comment.app;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.comment.api.dto.AddCommentRequestDto;
import com.dss.realworld.comment.api.dto.AddCommentResponseDto;
import com.dss.realworld.comment.api.dto.CommentAuthorDto;
import com.dss.realworld.comment.api.dto.GetCommentsResponseDto;
import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.comment.domain.repository.CommentRepository;
import com.dss.realworld.common.dto.AuthorDto;
import com.dss.realworld.error.exception.ArticleNotFoundException;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public AddCommentResponseDto add(AddCommentRequestDto addCommentRequestDto, Long logonUserId, String slug) {
        Article foundArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);

        Comment comment = Comment.builder()
                .body(addCommentRequestDto.getBody())
                .articleId(foundArticle.getId())
                .userId(logonUserId) // todo 로그인 안 했을 때 예외 추가
                .build();

        commentRepository.add(comment);
        Comment foundComment = commentRepository.getById(comment.getId());
        AuthorDto author = getAuthor(comment.getUserId());

        return new AddCommentResponseDto(foundComment, author);
    }

    @Override
    @Transactional
    public int deleteComment(final String slug, final Long commentId, Long userId) {
        Optional<Article> article = articleRepository.findBySlug(slug);
        return commentRepository.deleteComment(commentId, article.get().getId(), userId);
    }

    @Override
    public List<CommentAuthorDto> getComments(final String slug) {

        Article foundArticle = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);

        List<Comment> comments = commentRepository.getComments(foundArticle.getId());

        List<CommentAuthorDto> commentAuthorDtoList = new ArrayList<>();
        List<GetCommentsResponseDto> getCommentAuthorDtoList = new ArrayList<>();

        for (Comment comment : comments) {
            AuthorDto author = getAuthor(comment.getUserId());
            CommentAuthorDto commentAuthorDto = CommentAuthorDto.of(comment.getId(), comment.getBody(), author, comment.getCreatedAt(), comment.getUpdatedAt());
            commentAuthorDtoList.add(commentAuthorDto);
        }
        return commentAuthorDtoList;
    }

    @Override
    public AuthorDto getAuthor(Long userId) {
        User foundAuthor = userRepository.findById(userId);
        return AuthorDto.of(foundAuthor.getUsername(), foundAuthor.getBio(), foundAuthor.getImage());
    }
}