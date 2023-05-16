package com.dss.realworld.user.app;

import com.dss.realworld.user.api.ProfileResponseDto;
import com.dss.realworld.user.domain.FollowRelation;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.FollowRelationRepository;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final FollowRelationRepository followRelationRepository;

    @Override
    public ProfileResponseDto getProfileDto(String username) {
        User user = userRepository.findByUsername(username);
        return ProfileResponseDto.of(user);
    }

    @Override
    public ProfileResponseDto followUser(String username, Long toUserId) {
        User fromUser = userRepository.findByUsername(username);
        int followCheck = followRelationRepository.checkFollowing(fromUser.getId(), toUserId);
        if (followCheck >= 1) {
            return unFollowUser(username, toUserId);
        } else {
            FollowRelation followRelation = new FollowRelation(fromUser.getId(), toUserId);
            followRelationRepository.save(followRelation);

            ProfileResponseDto profileResponseDto = ProfileResponseDto.builder()
                    .username(fromUser.getUsername())
                    .bio(fromUser.getBio())
                    .image(fromUser.getImage())
                    .following(true)
                    .build();

            return profileResponseDto;
        }
    }

    @Override
    public ProfileResponseDto unFollowUser(String username, Long toUserId) {
        User fromUser = userRepository.findByUsername(username);
        ProfileResponseDto profileResponseDto = ProfileResponseDto.builder()
                .username(fromUser.getUsername())
                .bio(fromUser.getBio())
                .image(fromUser.getImage())
                .following(false)
                .build();

        followRelationRepository.cancelFollow(fromUser.getId(), toUserId);
        return profileResponseDto;
    }
}