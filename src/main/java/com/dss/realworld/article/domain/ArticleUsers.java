package com.dss.realworld.article.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import org.springframework.util.Assert;

@Alias(value = "ArticleUsers")
@Getter
@NoArgsConstructor
public class ArticleUsers {

    private Long id;
    private Long articleId;
    private Long favoritedId;

    public ArticleUsers(final Long articleId, final Long favoritedId) {
        Assert.notNull(articleId, "articleId can not be null");
        Assert.notNull(favoritedId, "favoritedId can not be null");

        this.articleId = articleId;
        this.favoritedId = favoritedId;
    }
}