package com.dss.realworld.user.app;

import com.dss.realworld.user.api.dto.AddUserRequestDto;
import com.dss.realworld.user.api.dto.UpdateUserRequestDto;
import com.dss.realworld.user.domain.User;

public interface UserService {

    User save(AddUserRequestDto addUserRequestDto);

    User update(UpdateUserRequestDto updateUserRequestDto, Long userId);
}