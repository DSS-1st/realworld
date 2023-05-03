package com.dss.realworld.comment.app;

import com.dss.realworld.article.domain.dto.GetArticleDto;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.comment.domain.dto.AddCommentRequestDto;
import com.dss.realworld.comment.domain.dto.GetCommentAuthorDto;
import com.dss.realworld.comment.domain.dto.GetCommentDto;
import com.dss.realworld.comment.domain.repository.CommentRepository;
import com.dss.realworld.user.domain.repository.GetUserDto;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public GetCommentAuthorDto addComment(AddCommentRequestDto addCommentRequestDto, Long logonUserId, String slug) {
        GetArticleDto foundArticle = articleRepository.getArticleBySlug(slug);

        Comment comment = Comment.builder()
                .body(addCommentRequestDto.getComment().getBody())
                .articleId(foundArticle.getId())
                .userId(logonUserId)
                .build();

        commentRepository.add(comment);
        GetCommentDto foundComment = commentRepository.getById(comment.getId());
        GetUserDto foundUser = userRepository.getById(comment.getUserId());

        return GetCommentAuthorDto.builder()
                .id(foundComment.getId())
                .body(foundComment.getBody())
                .author(foundUser)
                .createdAt(foundComment.getCreatedAt())
                .updatedAt(foundComment.getUpdatedAt())
                .build();
    }
}
