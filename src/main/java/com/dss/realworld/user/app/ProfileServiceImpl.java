package com.dss.realworld.user.app;

import com.dss.realworld.user.api.dto.ProfileResponseDto;
import com.dss.realworld.user.domain.FollowRelation;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.FollowRelationRepository;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final FollowRelationRepository followRelationRepository;

    @Override
    public ProfileResponseDto get(String username, Long toUserId) {
        User fromUser = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        int result = followRelationRepository.checkFollowing(fromUser.getId(), toUserId);
        if (result >= 1) {

            return getProfileResponseDto(fromUser, true);
        }

        return getProfileResponseDto(fromUser,false);
    }

    @Override
    @Transactional
    public ProfileResponseDto follow(String username, Long toUserId) {
        User fromUser = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        FollowRelation followRelation = new FollowRelation(fromUser.getId(), toUserId);
        followRelationRepository.save(followRelation);

       return getProfileResponseDto(fromUser, true);
    }

    @Override
    public ProfileResponseDto unFollow(String username, Long toUserId) {
        User fromUser = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        ProfileResponseDto profileResponseDto = getProfileResponseDto(fromUser, false);

        followRelationRepository.delete(fromUser.getId(), toUserId);

        return getProfileResponseDto(fromUser,false);
    }

    private static ProfileResponseDto getProfileResponseDto(User fromUser, boolean following) {
        ProfileResponseDto profileResponseDto = ProfileResponseDto.builder()
                .username(fromUser.getUsername())
                .bio(fromUser.getBio())
                .image(fromUser.getImage())
                .following(following)
                .build();

        return profileResponseDto;
    }
}