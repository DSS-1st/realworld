package com.dss.realworld.user.api;

import com.dss.realworld.user.app.UserService;
import com.dss.realworld.user.domain.repository.GetUserDto;
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
    public AddUserResponseDto addUser(@RequestBody AddUserRequestDto addUserRequestDto) {
        GetUserDto getUserDto = userService.addUser(addUserRequestDto);
        return this.getAddUserResponseDto(getUserDto);
    }

    private AddUserResponseDto getAddUserResponseDto(GetUserDto getUserDto) {
        AddUserResponseDto.AddUserDto addUserDto = AddUserResponseDto.AddUserDto.builder()
                .email(getUserDto.getEmail())
                .username(getUserDto.getUsername())
                .bio(getUserDto.getBio())
                .image(getUserDto.getImage())
                .token("token")
                .build();
        return AddUserResponseDto.builder().user(addUserDto).build();
    }
}
