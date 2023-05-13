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
    public GetProfileDto getProfile(@PathVariable String username) {
       return profileService.getProfileDto(username);
    }
}