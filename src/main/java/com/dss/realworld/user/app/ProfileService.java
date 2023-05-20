package com.dss.realworld.user.app;

import com.dss.realworld.user.api.ProfileResponseDto;

public interface ProfileService {

    ProfileResponseDto getProfile(String username, Long toUserId);

    ProfileResponseDto followUser(String username, Long toUserId);

    ProfileResponseDto unFollow(String username, Long toUserId);
}