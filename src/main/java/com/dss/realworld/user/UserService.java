package com.dss.realworld.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User addUser(AddUserRequestDto addUserRequestDto) {
        User user = User.builder()
                .username(addUserRequestDto.getUser().getUsername())
                .email(addUserRequestDto.getUser().getEmail())
                .password(addUserRequestDto.getUser().getPassword())
                .build();
        userRepository.addUser(user);
        return user;
    }
}
