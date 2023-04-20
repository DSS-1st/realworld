package com.dss.realworld.user.api;

import com.dss.realworld.user.app.UserService;
import com.dss.realworld.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public User addUser(@RequestBody AddUserRequestDto addUserRequestDto) {
        return userService.addUser(addUserRequestDto);
    }
}
