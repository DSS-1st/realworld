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
       return profileService.getProfileDto(username);
    }

    @PostMapping(value = "/profiles/{username}/follow")
    public ProfileResponseDto followUser(@PathVariable String username) {
       return profileService.followUser(username,getLogonUserId());
    }

    @DeleteMapping(value = "/profiles/{username}/follow")
    public ProfileResponseDto unFollowUser(@PathVariable String username) {
        return profileService.unFollowUser(username,getLogonUserId());
    }

    private Long getLogonUserId(){
        return 1L;
    }
}