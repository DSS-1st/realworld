package com.dss.realworld.user.app;

import com.dss.realworld.user.api.GetProfileDto;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    @Override
    public GetProfileDto getProfileDto(String username) {
        User user = userRepository.findByUsername(username);
        return GetProfileDto.of(user);
    }
}