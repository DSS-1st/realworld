package com.dss.realworld.user.api;

import com.dss.realworld.user.api.dto.AddUserRequestDto;
import com.dss.realworld.user.api.dto.LoginUserRequestDto;
import com.dss.realworld.user.api.dto.UpdateUserRequestDto;
import com.dss.realworld.user.api.dto.UserResponseDto;
import com.dss.realworld.user.app.UserService;
import com.dss.realworld.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/users")
    public UserResponseDto add(@RequestBody AddUserRequestDto addUserRequestDto) {
        User user = userService.save(addUserRequestDto);

        return new UserResponseDto(user);
    }

    @PutMapping(value = "/user")
    public UserResponseDto update(@RequestBody UpdateUserRequestDto updateUserRequestDto) {
        User user = userService.update(updateUserRequestDto, getLoginUserId());

        return new UserResponseDto(user);
    }

    @PostMapping(value = "/users/login")
    public UserResponseDto login(@RequestBody LoginUserRequestDto loginUserRequestDto) {
        User user = userService.login(loginUserRequestDto);

        return new UserResponseDto(user);
    }

    private Long getLoginUserId() {
        return 1L;
    }
}

//todo ResponseEntity를 반환하도록 수정