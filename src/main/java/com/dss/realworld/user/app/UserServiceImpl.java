package com.dss.realworld.user.app;

import com.dss.realworld.user.api.dto.AddUserRequestDto;
import com.dss.realworld.user.api.dto.UpdateUserRequestDto;
import com.dss.realworld.error.exception.PasswordNotMatchedException;
import com.dss.realworld.error.exception.UserNotFoundException;
import com.dss.realworld.user.api.LoginUserRequestDto;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
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

        return userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public User update(UpdateUserRequestDto updateUserRequestDto, Long userId) {
        User findUser = userRepository.findById(userId);
        User user = findUser.builder()
                .email(updateUserRequestDto.getEmail())
                .username(updateUserRequestDto.getUsername())
                .password(updateUserRequestDto.getPassword())
                .bio(updateUserRequestDto.getBio())
                .image(updateUserRequestDto.getImage()).build();

        userRepository.update(user, userId);

        return userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public User login(LoginUserRequestDto loginUserRequestDto) {
        User user = userRepository.findByEmail(loginUserRequestDto.getEmail()).orElseThrow(() -> new UserNotFoundException());

        checkPassword(loginUserRequestDto, user);

        return user;
    }

    private static void checkPassword(LoginUserRequestDto loginUserRequestDto, User user) {
        if (loginUserRequestDto.getPassword() != user.getPassword()) {
            throw new PasswordNotMatchedException();
        }
    }
}