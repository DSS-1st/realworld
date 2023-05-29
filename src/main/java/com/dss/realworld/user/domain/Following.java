package com.dss.realworld.user.domain;

import com.dss.realworld.error.exception.CustomApiException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import org.springframework.util.Assert;

@Alias(value = "Following")
@Getter
@NoArgsConstructor
public class Following {

    private Long id;
    private Long targetId;
    private Long loginId;

    public Following(Long id, Long targetId, Long loginId) {
        Assert.notNull(targetId, "targetId can not be null");
        Assert.notNull(loginId, "loginId can not be null");
        isSelfFollowing(targetId, loginId);

        this.id = id;
        this.targetId = targetId;
        this.loginId = loginId;
    }

    private void isSelfFollowing(final Long targetId, final Long loginId) {
        if(targetId.equals(loginId)) throw new CustomApiException("자기 자신을 팔로우할 수 없습니다.");
    }

    public Following(Long targetId, Long loginId) {
        Assert.notNull(targetId, "targetId can not be null");
        Assert.notNull(loginId, "loginId can not be null");
        isSelfFollowing(targetId, loginId);

        this.targetId = targetId;
        this.loginId = loginId;
    }
}