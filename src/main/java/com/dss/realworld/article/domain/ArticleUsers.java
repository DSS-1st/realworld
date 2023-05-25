package com.dss.realworld.article.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias(value = "ArticleUsers")
@Getter
@NoArgsConstructor
public class ArticleUsers {

    private Long id;
    private Long articleId;
    private Long loginId;

    public ArticleUsers(final Long id, final Long articleId, final Long loginId) {
        this.id = id;
        this.articleId = articleId;
        this.loginId = loginId;
    }

    public ArticleUsers(final Long articleId, final Long loginId) {
        this.articleId = articleId;
        this.loginId = loginId;
    }
}
