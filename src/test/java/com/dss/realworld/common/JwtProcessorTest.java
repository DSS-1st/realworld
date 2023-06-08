package com.dss.realworld.common;

import com.dss.realworld.common.auth.LoginUser;
import com.dss.realworld.common.error.exception.UserNotFoundException;
import com.dss.realworld.common.jwt.JwtProcessor;
import com.dss.realworld.common.jwt.JwtVO;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.dss.realworld.util.UserFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles(value = "test")
@Sql(value = "classpath:db/teardown.sql")
@SpringBootTest
public class JwtProcessorTest {

    @Autowired
    JwtProcessor jwtProcessor;

    @Autowired
    UserRepository userRepository;

    @DisplayName(value = "User 객체를 통해 토큰 생성 성공")
    @Test
    void t1() {
        //given
        User user = UserFixtures.create(100L, "tom", "tom@realworld.com", "tomandtoms");
        LoginUser loginUser = new LoginUser(user);

        //when
        String jwtToken = jwtProcessor.create(loginUser);
        System.out.println("jwtToken = " + jwtToken);

        //then
        assertThat(jwtToken.startsWith(JwtVO.TOKEN_PREFIX)).isTrue();
    }

    @DisplayName(value = "생성된 토큰 검증 성공")
    @Test
    void t2() {
        //given
        String email = "tom@realworld.com";
        User user = UserFixtures.create(100L, "tom", email, "tomandtoms");
        userRepository.persist(user);
        String jwtToken = jwtProcessor.create(new LoginUser(user)).replace(JwtVO.TOKEN_PREFIX, "");

        //when
        LoginUser loginUser = jwtProcessor.verify(jwtToken);

        //then
        assertThat(loginUser.getUser().getEmail()).isEqualTo(email);
    }

    @DisplayName(value = "존재하지 않는 사용자로 검증 시 예외 발생")
    @Test
    void t3() {
        //given
        String email = "non_existent_user@realworld.com";
        String token = jwtProcessor.create(email).replace(JwtVO.TOKEN_PREFIX, "");

        //when
        //then
        assertThatThrownBy(() -> jwtProcessor.verify(token)).isInstanceOf(UserNotFoundException.class).hasMessageContaining("해당하는 사용자를 찾을 수 없습니다.");
    }
}