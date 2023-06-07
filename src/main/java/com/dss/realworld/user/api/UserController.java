package com.dss.realworld.user.api;

import com.dss.realworld.user.api.dto.AddUserRequestDto;
import com.dss.realworld.user.api.dto.LoginRequestDto;
import com.dss.realworld.user.api.dto.UpdateUserRequestDto;
import com.dss.realworld.user.api.dto.UserResponseDto;
import com.dss.realworld.user.app.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/api")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/users")
    public ResponseEntity<UserResponseDto> add(@RequestBody @Valid AddUserRequestDto addUserRequestDto, BindingResult bindingResult) {
        UserResponseDto userResponseDto = userService.save(addUserRequestDto);

        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping(value = "/user")
    public ResponseEntity<UserResponseDto> update(@RequestBody @Valid UpdateUserRequestDto updateUserRequestDto, BindingResult bindingResult) {
        UserResponseDto userResponseDto = userService.update(updateUserRequestDto, getLoginUserId());

        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping(value = "/users/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        UserResponseDto userResponseDto = userService.login(loginRequestDto);

        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        UserResponseDto userResponseDto = userService.get(getLoginUserId());

        return ResponseEntity.ok(userResponseDto);
    }

    private Long getLoginUserId() {
        return 1L;
    }
}