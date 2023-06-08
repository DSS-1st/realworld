package com.dss.realworld.user.api;

import com.dss.realworld.common.auth.LoginUser;
import com.dss.realworld.user.api.dto.ProfileResponseDto;
import com.dss.realworld.user.app.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping(value = "/profiles/{username}")
    public ResponseEntity<ProfileResponseDto> get(@PathVariable final String username,
                                                  @AuthenticationPrincipal final LoginUser loginUser) {
        ProfileResponseDto profileResponseDto;
        if (loginUser == null) profileResponseDto = profileService.get(username, null);
        else profileResponseDto = profileService.get(username, loginUser.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(profileResponseDto);
    }

    @PostMapping(value = "/profiles/{username}/follow")
    public ResponseEntity<ProfileResponseDto> follow(@PathVariable final String username,
                                                     @AuthenticationPrincipal final LoginUser loginUser) {
        ProfileResponseDto profileResponseDto = profileService.follow(username, loginUser.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(profileResponseDto);
    }

    @DeleteMapping(value = "/profiles/{username}/follow")
    public ResponseEntity<ProfileResponseDto> unfollow(@PathVariable final String username,
                                                       @AuthenticationPrincipal final LoginUser loginUser) {
        ProfileResponseDto profileResponseDto = profileService.unFollow(username, loginUser.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(profileResponseDto);
    }
}