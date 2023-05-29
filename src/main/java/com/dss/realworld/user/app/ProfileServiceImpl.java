package com.dss.realworld.user.app;

import com.dss.realworld.error.exception.UserNotFoundException;
import com.dss.realworld.user.api.dto.ProfileResponseDto;
import com.dss.realworld.user.domain.Following;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.FollowingRepository;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final FollowingRepository followingRepository;

    @Override
    public ProfileResponseDto get(String username, Long loginId) {
        User targetUser = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        int result = followingRepository.isFollowing(targetUser.getId(), loginId);

        return getProfileResponseDto(targetUser, result == 1);
    }

    @Transactional
    @Override
    public ProfileResponseDto follow(String username, Long loginId) {
        User targetUser = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        Following following = new Following(targetUser.getId(), loginId);
        followingRepository.persist(following);

        return getProfileResponseDto(targetUser, true);
    }

    @Transactional
    @Override
    public ProfileResponseDto unFollow(String username, Long loginId) {
        User targetUser = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        followingRepository.delete(targetUser.getId(), loginId);

        return getProfileResponseDto(targetUser, false);
    }

    private ProfileResponseDto getProfileResponseDto(User targetUser, boolean isFollow) {
        return ProfileResponseDto.builder()
                .username(targetUser.getUsername())
                .bio(targetUser.getBio())
                .image(targetUser.getImage())
                .following(isFollow)
                .build();
    }
}