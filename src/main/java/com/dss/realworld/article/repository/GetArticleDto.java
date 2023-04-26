package com.dss.realworld.article.repository;

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
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
