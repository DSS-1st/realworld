package com.dss.realworld.user.app;

import com.dss.realworld.error.exception.UserNotFoundException;
import com.dss.realworld.user.api.dto.ProfileResponseDto;
import com.dss.realworld.user.domain.FollowRelation;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.FollowRelationRepository;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final FollowRelationRepository followRelationRepository;

    @Override
    public ProfileResponseDto get(String username, Long loginUserId) {
        User targetUser = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        int result = followRelationRepository.isFollowing(targetUser.getId(), loginUserId);

        return getProfileResponseDto(targetUser, result == 1);
    }

    @Transactional
    @Override
    public ProfileResponseDto follow(String username, Long loginUserId) {
        User targetUser = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        FollowRelation followRelation = new FollowRelation(targetUser.getId(), loginUserId);
        followRelationRepository.persist(followRelation);

        return getProfileResponseDto(targetUser, true);
    }

    @Transactional
    @Override
    public ProfileResponseDto unFollow(String username, Long loginUserId) {
        User targetUser = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        followRelationRepository.delete(targetUser.getId(), loginUserId);

        return getProfileResponseDto(targetUser, false);
    }

    private ProfileResponseDto getProfileResponseDto(User targetUser, boolean isFollow) {
        ProfileResponseDto profileResponseDto = ProfileResponseDto.builder()
                .username(targetUser.getUsername())
                .bio(targetUser.getBio())
                .image(targetUser.getImage())
                .following(isFollow)
                .build();

        return profileResponseDto;
    }
}