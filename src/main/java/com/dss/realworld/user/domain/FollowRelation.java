package com.dss.realworld.user.domain;

import org.apache.ibatis.type.Alias;

@Alias(value = "FollowRelation")
public class FollowRelation {

    private Long fromUserId;

    private Long toUserId;

    public FollowRelation( Long fromUserId, Long toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }
}