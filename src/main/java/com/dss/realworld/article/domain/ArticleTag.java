package com.dss.realworld.article.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias(value = "ArticleTag")
@Getter
@NoArgsConstructor
public class ArticleTag {

    private Long id;
    private Long articleId;
    private Long tagId;

    public ArticleTag(final Long articleId, final Long tagId) {
        this.articleId = articleId;
        this.tagId = tagId;
    }
}