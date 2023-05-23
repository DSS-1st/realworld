package com.dss.realworld.user.api;

import com.dss.realworld.user.api.dto.ProfileResponseDto;
import com.dss.realworld.user.app.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping(value = "/profiles/{username}")
    public ResponseEntity<ProfileResponseDto> get(@PathVariable String username) {
        ProfileResponseDto profileResponseDto = profileService.get(username, getLoginId());

        return new ResponseEntity<>(profileResponseDto, HttpStatus.OK);
    }

    @PostMapping(value = "/profiles/{username}/follow")
    public ResponseEntity<ProfileResponseDto> follow(@PathVariable String username) {
        ProfileResponseDto profileResponseDto = profileService.follow(username, getLoginId());

        return new ResponseEntity<>(profileResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/profiles/{username}/follow")
    public ResponseEntity<ProfileResponseDto> unfollow(@PathVariable String username) {
        ProfileResponseDto profileResponseDto = profileService.unFollow(username, getLoginId());

        return new ResponseEntity<>(profileResponseDto, HttpStatus.OK);
    }

    private Long getLoginId() {
        return 1L;
    }
}