package com.dss.realworld.user.app;

import com.dss.realworld.common.error.exception.DuplicateEmailException;
import com.dss.realworld.common.error.exception.DuplicateUsernameException;
import com.dss.realworld.common.error.exception.PasswordNotMatchedException;
import com.dss.realworld.common.error.exception.UserNotFoundException;
import com.dss.realworld.common.jwt.JwtProcessor;
import com.dss.realworld.user.api.dto.AddUserRequestDto;
import com.dss.realworld.user.api.dto.LoginRequestDto;
import com.dss.realworld.user.api.dto.UpdateUserRequestDto;
import com.dss.realworld.user.api.dto.UserResponseDto;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProcessor jwtProcessor;

    @Transactional
    @Override
    public UserResponseDto save(final AddUserRequestDto addUserRequestDto) {
        User user = User.builder()
                .username(addUserRequestDto.getUsername())
                .email(addUserRequestDto.getEmail())
                .passwordEncoder(passwordEncoder)
                .password(addUserRequestDto.getPassword())
                .build();
        checkDuplicateUser(user);
        userRepository.persist(user);

        User foundUser = userRepository.findByEmail(user.getEmail()).orElseThrow(UserNotFoundException::new);

        return new UserResponseDto(foundUser, jwtProcessor.create(foundUser.getEmail()));
    }

    private void checkDuplicateUser(final User user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(foundUser -> {
            throw new DuplicateUsernameException();
        });
        userRepository.findByEmail(user.getEmail()).ifPresent(foundUser -> {
            throw new DuplicateEmailException();
        });
    }

    @Transactional
    @Override
    public UserResponseDto update(final UpdateUserRequestDto updateUserRequestDto, final Long loginId) {
        User foundUser = userRepository.findById(loginId).orElseThrow(UserNotFoundException::new);
        User updateValue = foundUser.builder()
                .email(updateUserRequestDto.getEmail())
                .username(updateUserRequestDto.getUsername())
                .passwordEncoder(passwordEncoder)
                .password(updateUserRequestDto.getPassword())
                .bio(updateUserRequestDto.getBio())
                .image(updateUserRequestDto.getImage()).build();
        userRepository.update(updateValue, loginId);

        User updatedUser = userRepository.findByEmail(updateValue.getEmail()).orElseThrow(UserNotFoundException::new);

        return new UserResponseDto(updatedUser, jwtProcessor.create(updatedUser.getEmail()));
    }

    @Override
    public UserResponseDto login(final LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(UserNotFoundException::new);
        if (!user.isMatch(loginRequestDto, passwordEncoder)) throw new PasswordNotMatchedException();

        return new UserResponseDto(user, jwtProcessor.create(user.getEmail()));
    }

    @Override
    public UserResponseDto get(final Long loginId) {
        User foundUser = userRepository.findById(loginId).orElseThrow(UserNotFoundException::new);

        return new UserResponseDto(foundUser, jwtProcessor.create(foundUser.getEmail()));
    }
}