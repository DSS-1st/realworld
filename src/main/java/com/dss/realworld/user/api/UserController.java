package com.dss.realworld.user.api;

import com.dss.realworld.user.api.dto.AddUserRequestDto;
import com.dss.realworld.user.api.dto.LoginUserRequestDto;
import com.dss.realworld.user.api.dto.UpdateUserRequestDto;
import com.dss.realworld.user.api.dto.UserResponseDto;
import com.dss.realworld.user.app.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/users")
    public ResponseEntity<UserResponseDto> add(@RequestBody AddUserRequestDto addUserRequestDto) {
        UserResponseDto userResponseDto = userService.save(addUserRequestDto);

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/user")
    public ResponseEntity<UserResponseDto> update(@RequestBody UpdateUserRequestDto updateUserRequestDto) {
        UserResponseDto userResponseDto = userService.update(updateUserRequestDto, getLoginUserId());

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PostMapping(value = "/users/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginUserRequestDto loginUserRequestDto) {
        UserResponseDto userResponseDto = userService.login(loginUserRequestDto);

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        UserResponseDto userResponseDto = userService.get(getLoginUserId());

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    private Long getLoginUserId() {
        return 1L;
    }
}

//todo ResponseEntity를 반환하도록 수정