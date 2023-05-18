package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.FollowRelation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowRelationRepository {

    int save(FollowRelation followRelation);

    int delete (Long fromUserId, Long toUserId);

    int checkFollowing(Long fromUserId, Long toUserId);

    void deleteAll();

    void resetAutoIncrement();
}