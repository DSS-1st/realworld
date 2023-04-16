package com.dss.realworld.user;

//todo 완성 후 인터페이스 추출

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void addUser(AddUserDto addUserDto) {
        Users users = Users.builder()
                .username(addUserDto.getUsername())
                .email(addUserDto.getEmail())
                .password(addUserDto.getPassword())
                .build();
        userRepository.addUser(users);
    }
}
