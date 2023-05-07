package com.dss.realworld.article.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Alias("GetArticleDto")
public class GetArticleDto {

    private Long id;
    private String slug;
    private String title;
    private String description;
    private String body;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
