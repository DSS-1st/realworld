package com.dss.realworld.user.api;

import com.dss.realworld.common.auth.LoginUser;
import com.dss.realworld.common.error.CustomExceptionHandler;
import com.dss.realworld.user.api.dto.AddUserRequestDto;
import com.dss.realworld.user.api.dto.LoginRequestDto;
import com.dss.realworld.user.api.dto.UpdateUserRequestDto;
import com.dss.realworld.user.api.dto.UserResponseDto;
import com.dss.realworld.user.app.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/api")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/users")
    public ResponseEntity<UserResponseDto> add(@RequestBody @Valid final AddUserRequestDto addUserRequestDto,
                                               final BindingResult bindingResult) {
        CustomExceptionHandler.checkOrThrow(bindingResult);

        UserResponseDto userResponseDto = userService.save(addUserRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @PutMapping(value = "/user")
    public ResponseEntity<UserResponseDto> update(@RequestBody @Valid final UpdateUserRequestDto updateUserRequestDto,
                                                  final BindingResult bindingResult,
                                                  @AuthenticationPrincipal final LoginUser loginUser) {
        CustomExceptionHandler.checkOrThrow(bindingResult);

        UserResponseDto userResponseDto = userService.update(updateUserRequestDto, loginUser.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @PostMapping(value = "/users/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody @Valid final LoginRequestDto loginRequestDto) {
        UserResponseDto userResponseDto = userService.login(loginRequestDto);

        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<UserResponseDto> getCurrentUser(@AuthenticationPrincipal final LoginUser loginUser) {
        UserResponseDto userResponseDto = userService.get(loginUser.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }
}