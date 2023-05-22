package com.dss.realworld.user.app;

import com.dss.realworld.user.api.LoginUserRequestDto;
import com.dss.realworld.user.api.dto.AddUserRequestDto;
import com.dss.realworld.user.api.dto.UpdateUserRequestDto;
import com.dss.realworld.user.domain.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    @Transactional
    User save(AddUserRequestDto addUserRequestDto);

    User update(UpdateUserRequestDto updateUserRequestDto, Long userId);

    User login(LoginUserRequestDto loginUserRequestDto);
}