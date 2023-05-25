package com.dss.realworld.article.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import org.springframework.util.Assert;

@Alias(value = "ArticleTag")
@Getter
@NoArgsConstructor
public class ArticleTag {

    private Long id;
    private Long articleId;
    private Long tagId;

    public ArticleTag(final Long articleId, final Long tagId) {
        Assert.notNull(articleId, "articleId can not be null");
        Assert.notNull(tagId, "tagId can not be null");

        this.articleId = articleId;
        this.tagId = tagId;
    }
}