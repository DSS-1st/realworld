package com.dss.realworld.user.api;

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
    public AddUserResponseDto add(@RequestBody AddUserRequestDto addUserRequestDto) {
        User user = userService.save(addUserRequestDto);

        return new AddUserResponseDto(user);
    }

    @PutMapping(value = "/users")
    public AddUserResponseDto update(@RequestBody UpdateUserRequestDto updateUserRequestDto) {
        User user = userService.update(updateUserRequestDto,getLoginUserId());

        return new AddUserResponseDto(user);
    }

    private Long getLoginUserId() {
        return 1L;
    }
}