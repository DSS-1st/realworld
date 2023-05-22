package com.dss.realworld.user.app;

import com.dss.realworld.user.api.dto.ProfileResponseDto;

public interface ProfileService {

    ProfileResponseDto get(String username, Long loginId);

    ProfileResponseDto follow(String username, Long loginId);

    ProfileResponseDto unFollow(String username, Long loginId);
}