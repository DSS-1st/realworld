package com.dss.realworld.user.app;

import com.dss.realworld.user.api.dto.ProfileResponseDto;

public interface ProfileService {

    ProfileResponseDto get(String username, Long loginUserId);

    ProfileResponseDto follow(String username, Long loginUserId);

    ProfileResponseDto unFollow(String username, Long loginUserId);
}