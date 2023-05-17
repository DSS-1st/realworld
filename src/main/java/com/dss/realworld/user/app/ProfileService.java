package com.dss.realworld.user.app;

import com.dss.realworld.user.api.ProfileResponseDto;

public interface ProfileService {

    ProfileResponseDto get(String username);

    ProfileResponseDto followUser(String username,Long followerId);
}