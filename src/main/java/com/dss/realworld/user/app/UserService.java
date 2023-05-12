package com.dss.realworld.user.app;

import com.dss.realworld.user.api.AddUserRequestDto;
import com.dss.realworld.user.domain.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    @Transactional
    User save(AddUserRequestDto addUserRequestDto);
}
