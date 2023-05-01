package com.dss.realworld.comment.domain.dto;

import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Getter
@Alias("GetCommentDto")
public class GetCommentDto {

    private Long id;
    private Long articleId;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;

    @Builder
    public GetCommentDto(Long id,
                         Long articleId,
                         String body,
                         LocalDateTime createdAt,
                         LocalDateTime updatedAt,
                         Long userId
                         ) {
        this.id = id;
        this.articleId = articleId;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }
}
