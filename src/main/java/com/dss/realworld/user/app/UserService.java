package com.dss.realworld.user.app;

import com.dss.realworld.user.api.dto.AddUserRequestDto;
import com.dss.realworld.user.api.dto.LoginUserRequestDto;
import com.dss.realworld.user.api.dto.UpdateUserRequestDto;
import com.dss.realworld.user.api.dto.UserResponseDto;

public interface UserService {

    UserResponseDto save(AddUserRequestDto addUserRequestDto);

    UserResponseDto update(UpdateUserRequestDto updateUserRequestDto, Long userId);

    UserResponseDto login(LoginUserRequestDto loginUserRequestDto);

    UserResponseDto get(Long loginUserId);
}