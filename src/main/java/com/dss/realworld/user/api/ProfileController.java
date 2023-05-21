package com.dss.realworld.user.api;

import com.dss.realworld.user.app.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping(value = "/profiles/{username}")
    public ProfileResponseDto getProfile(@PathVariable String username) {
       return profileService.getProfile(username,getLogonUserId());
    }

    @PostMapping(value = "/profiles/{username}/follow")
    public ProfileResponseDto followUser(@PathVariable String username) {
       return profileService.follow(username,getLogonUserId());
    }

    @DeleteMapping(value = "/profiles/{username}/follow")
    public ProfileResponseDto unFollowUser(@PathVariable String username) {
        return profileService.unFollow(username,getLogonUserId());
    }

    private Long getLogonUserId(){
        return 1L;
    }
}