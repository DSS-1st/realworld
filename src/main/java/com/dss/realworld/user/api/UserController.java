package com.dss.realworld.user.api;

import com.dss.realworld.user.app.UserService;
import com.dss.realworld.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/users")
    public AddUserResponseDto add(@RequestBody AddUserRequestDto addUserRequestDto) {
        User user = userService.save(addUserRequestDto);

        return new AddUserResponseDto(user);
    }
}