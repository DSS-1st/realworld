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
       return profileService.get(username);
    }

    @PostMapping(value = "/profiles/{username}/follow")
    public ProfileResponseDto followUser(@PathVariable String username, @RequestParam Long followerId) {
       return profileService.followUser(username,followerId);
    }
}