package com.dss.realworld.article.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Alias(value = "GetArticleDto")
public class GetArticleDto {

    private Long id;
    private String slug;
    private String title;
    private String description;
    private String body;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public boolean isAuthorMatch(final Long userId){
        return this.userId.compareTo(userId) != 0;
    }
}
