package com.dss.realworld.comment.domain;

import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@Alias("Comment")
public class Comment {
    private final Long id;
    private final Long articleId;
    private final String body;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final LocalDateTime updatedAt = LocalDateTime.now();
    private final Long userId;

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
