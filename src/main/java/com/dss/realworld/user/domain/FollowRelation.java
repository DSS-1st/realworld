package com.dss.realworld.user.domain;

import org.apache.ibatis.type.Alias;

@Alias(value = "FollowRelation")
public class FollowRelation {

    private Long targetId;
    private Long loginId;

    public FollowRelation(Long targetId, Long loginId) {
        this.targetId = targetId;
        this.loginId = loginId;
    }
}