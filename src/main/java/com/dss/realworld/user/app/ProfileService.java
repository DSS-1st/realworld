package com.dss.realworld.user.app;

import com.dss.realworld.user.api.ProfileResponseDto;

public interface ProfileService {

    ProfileResponseDto get(String username, Long toUserId);

    ProfileResponseDto follow(String username, Long toUserId);

    ProfileResponseDto unFollow(String username, Long toUserId);
}