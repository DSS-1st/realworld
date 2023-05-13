package com.dss.realworld.user.app;

import com.dss.realworld.user.api.GetProfileDto;

public interface ProfileService {

    GetProfileDto getProfileDto(String username);
}