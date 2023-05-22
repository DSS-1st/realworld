package com.dss.realworld.user.api;

import com.dss.realworld.user.api.dto.ProfileResponseDto;
import com.dss.realworld.user.app.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping(value = "/profiles/{username}")
    public ProfileResponseDto get(@PathVariable String username) {
        return profileService.get(username, getLoginId());
    }

    @PostMapping(value = "/profiles/{username}/follow")
    public ProfileResponseDto follow(@PathVariable String username) {
        return profileService.follow(username, getLoginId());
    }

    @DeleteMapping(value = "/profiles/{username}/follow")
    public ProfileResponseDto unfollow(@PathVariable String username) {
        return profileService.unFollow(username, getLoginId());
    }

    private Long getLoginId() {
        return 1L;
    }
}