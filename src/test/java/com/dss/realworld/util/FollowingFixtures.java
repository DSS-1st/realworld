package com.dss.realworld.util;

import com.dss.realworld.user.domain.Following;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.FollowingRepository;

public class FollowingFixtures {

    public static void saveFollowingSample(final FollowingRepository followingRepository, final Long targetId1, final Long targetId2, final User loginUser) {
        followingRepository.persist(new Following(targetId1, loginUser.getId()));
        followingRepository.persist(new Following(targetId2, loginUser.getId()));
    }
}