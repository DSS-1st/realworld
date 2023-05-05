package com.dss.realworld.comment.domain;

import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@Alias(value = "Comment")
public class Comment {
    private Long id;
    private Long articleId;
    private String body;
    private LocalDateTime createdAt = null;
    private LocalDateTime updatedAt = LocalDateTime.now();
    private Long userId;

    @Builder
    public Comment(Long id, String body, Long userId, Long articleId ) {

        Assert.notNull(body,"body can not be null");
        Assert.notNull(userId,"userId can not be null");
        Assert.notNull(articleId,"articleId can not be null");

        this.id = id;
        this.body = body;
        this.userId = userId;
        this.articleId = articleId;
    }
}
