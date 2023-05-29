package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.Following;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FollowingRepository {

    int persist(Following following);

    int delete(@Param(value = "targetId") Long targetId, @Param(value = "loginId") Long loginId);

    int isFollowing(@Param(value = "targetId") Long targetId, @Param(value = "loginId") Long loginId);
}