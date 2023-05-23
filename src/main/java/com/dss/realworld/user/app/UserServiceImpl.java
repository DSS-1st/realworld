package com.dss.realworld.user.app;

import com.dss.realworld.error.exception.CustomApiException;
import com.dss.realworld.error.exception.UserNotFoundException;
import com.dss.realworld.user.api.dto.AddUserRequestDto;
import com.dss.realworld.user.api.dto.LoginUserRequestDto;
import com.dss.realworld.user.api.dto.UpdateUserRequestDto;
import com.dss.realworld.user.api.dto.UserResponseDto;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserResponseDto save(AddUserRequestDto addUserRequestDto) {
        User user = User.builder()
                .username(addUserRequestDto.getUsername())
                .email(addUserRequestDto.getEmail())
                .password(addUserRequestDto.getPassword())
                .build();
        userRepository.persist(user);

        User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new UserNotFoundException());

        return new UserResponseDto(user1);
    }

    @Transactional
    @Override
    public UserResponseDto update(UpdateUserRequestDto updateUserRequestDto, Long loginId) {
        User foundUser = userRepository.findById(loginId);
        User updateValue = foundUser.builder()
                .email(updateUserRequestDto.getEmail())
                .username(updateUserRequestDto.getUsername())
                .password(updateUserRequestDto.getPassword())
                .bio(updateUserRequestDto.getBio())
                .image(updateUserRequestDto.getImage()).build();
        userRepository.update(updateValue, loginId);

        User user = userRepository.findByEmail(updateValue.getEmail()).orElseThrow(() -> new UserNotFoundException());

        return new UserResponseDto(user);
    }

    @Override
    public UserResponseDto login(LoginUserRequestDto loginUserRequestDto) {
        User user = userRepository.findByEmail(loginUserRequestDto.getEmail()).orElseThrow(() -> new UserNotFoundException());
        if(!user.isMatch(loginUserRequestDto)) throw new CustomApiException("비밀번호가 일치하지 않습니다.");

        return new UserResponseDto(user);
    }

    @Override
    public UserResponseDto get(Long loginUserId) {
        User user = userRepository.findById(loginUserId);

        return new UserResponseDto(user);
    }
}