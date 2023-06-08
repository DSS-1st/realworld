package com.dss.realworld.user.app;

import com.dss.realworld.common.error.exception.DuplicateFollowingException;
import com.dss.realworld.common.error.exception.UserNotFoundException;
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
    public ProfileResponseDto get(final String username, final Long loginId) {
        User targetUser = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        int result = followingRepository.isFollowing(targetUser.getId(), loginId);

        return getProfileResponseDto(targetUser, result == 1);
    }

    @Transactional
    @Override
    public ProfileResponseDto follow(final String username, final Long loginId) {
        User targetUser = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        if (followingRepository.isFollowing(targetUser.getId(), loginId) != 0) throw new DuplicateFollowingException();

        Following following = new Following(targetUser.getId(), loginId);
        followingRepository.persist(following);

        return getProfileResponseDto(targetUser, true);
    }

    @Transactional
    @Override
    public ProfileResponseDto unFollow(final String username, final Long loginId) {
        User targetUser = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        followingRepository.delete(targetUser.getId(), loginId);

        return getProfileResponseDto(targetUser, false);
    }

    private ProfileResponseDto getProfileResponseDto(final User targetUser, final boolean isFollow) {
        return ProfileResponseDto.builder()
                .username(targetUser.getUsername())
                .bio(targetUser.getBio())
                .image(targetUser.getImage())
                .following(isFollow)
                .build();
    }
}