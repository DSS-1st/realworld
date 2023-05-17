package com.dss.realworld.user.domain;

import org.apache.ibatis.type.Alias;

@Alias(value = "FollowRelation")
public class FollowRelation {

    private Long followeeId;

    private Long followerId;


    public FollowRelation( Long followeeId, Long followerId) {
        this.followeeId = followeeId;
        this.followerId = followerId;
    }
}