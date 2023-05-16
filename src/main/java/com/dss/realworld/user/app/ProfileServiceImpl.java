package com.dss.realworld.user.app;

import com.dss.realworld.user.api.ProfileResponseDto;
import com.dss.realworld.user.domain.FollowRelation;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.FollowRelationRepository;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
//@Transactional(readOnly = true)
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
    public ProfileResponseDto followUser(String username,Long followerId) {
        User followUser = userRepository.findByUsername(username);
        FollowRelation followRelation = new FollowRelation(followUser.getId(), followerId);
        followRelationRepository.save(followRelation);

        ProfileResponseDto profileResponseDto = ProfileResponseDto.builder()
                .username(followUser.getUsername())
                .bio(followUser.getBio())
                .image(followUser.getImage())
                .following(true)
                .build();

        return profileResponseDto;
    }
}