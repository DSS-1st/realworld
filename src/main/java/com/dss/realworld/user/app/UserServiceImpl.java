package com.dss.realworld.user.app;

import com.dss.realworld.user.api.AddUserRequestDto;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User save(AddUserRequestDto addUserRequestDto) {
        User user = User.builder()
                .username(addUserRequestDto.getUsername())
                .email(addUserRequestDto.getEmail())
                .password(addUserRequestDto.getPassword())
                .build();
        userRepository.persist(user);

        return userRepository.findByEmail(user.getEmail());
    }
}