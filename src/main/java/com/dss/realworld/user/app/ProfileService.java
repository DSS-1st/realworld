package com.dss.realworld.user.app;

import com.dss.realworld.user.api.GetProfileDto;
import com.dss.realworld.user.domain.User;

public interface ProfileService {

    GetProfileDto getProfileDto(String username);
}
