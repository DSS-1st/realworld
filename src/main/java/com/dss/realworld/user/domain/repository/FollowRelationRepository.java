package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.FollowRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FollowRelationRepository {

    int persist(FollowRelation followRelation);

    int delete(@Param(value = "targetId") Long targetId, @Param(value = "loginId") Long loginId);

    int isFollowing(@Param(value = "targetId") Long targetId, @Param(value = "loginId") Long loginId);
}