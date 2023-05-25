package com.dss.realworld.user.domain;

import org.apache.ibatis.type.Alias;
import org.springframework.util.Assert;

@Alias(value = "FollowRelation")
public class FollowRelation {

    private Long targetId;
    private Long loginId;

    public FollowRelation(Long targetId, Long loginId) {
        Assert.notNull(targetId,"targetId can not be null");
        Assert.notNull(loginId,"loginId can not be null");

        this.targetId = targetId;
        this.loginId = loginId;
    }
}