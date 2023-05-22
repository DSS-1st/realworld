package com.dss.realworld.user.app;

import com.dss.realworld.user.api.dto.ProfileResponseDto;

public interface ProfileService {

    ProfileResponseDto get(String username);

    ProfileResponseDto follow(String username, Long followerId);
}