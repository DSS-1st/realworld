package com.dss.realworld.user.domain;

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

        this.id = id;
        this.targetId = targetId;
        this.loginId = loginId;
    }

    public Following(Long targetId, Long loginId) {
        Assert.notNull(targetId, "targetId can not be null");
        Assert.notNull(loginId, "loginId can not be null");

        this.targetId = targetId;
        this.loginId = loginId;
    }
}