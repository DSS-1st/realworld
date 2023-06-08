package com.dss.realworld.util;

import com.dss.realworld.comment.domain.Comment;

public class CommentFixtures {

    public static Comment create() {
        return Comment.builder()
                .articleId(1L)
                .body("His name was my name too.")
                .userId(1L)
                .build();
    }

    public static Comment create(Long authorId) {
        return Comment.builder()
                .articleId(1L)
                .body("His name was my name too.")
                .userId(authorId)
                .build();
    }
}
