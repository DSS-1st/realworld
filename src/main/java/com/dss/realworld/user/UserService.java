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
    public User addUser(AddUserRequestDto addUserRequestDto) {
        User user = User.builder()
                .username(addUserRequestDto.getUser().getUsername())
                .email(addUserRequestDto.getUser().getEmail())
                .password(addUserRequestDto.getUser().getPassword())
                .build();
        userRepository.addUser(user);
        return user;  //todo 리파지토리에 getUser메서드 만들어서 처리 예정
    }
}
