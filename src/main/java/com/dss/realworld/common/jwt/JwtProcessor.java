package com.dss.realworld.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dss.realworld.common.auth.LoginUser;
import com.dss.realworld.common.error.exception.UserNotFoundException;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProcessor {

    @Value(value = "${jwt.secret}")
    private String secret;

    @Value(value = "${jwt.expirationTime}")
    private int expirationTime;

    private final UserRepository userRepository;

    public String create(final LoginUser loginUser) {
        String jwtToken = JWT.create()
                .withSubject(loginUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC512(secret));

        return JwtVO.TOKEN_PREFIX + jwtToken;
    }

    public String create(final String email) {
        String jwtToken = JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC512(secret));

        return JwtVO.TOKEN_PREFIX + jwtToken;
    }

    public LoginUser verify(final String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secret)).build().verify(token);
        String email = decodedJWT.getSubject();
        User foundUser = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        return new LoginUser(foundUser);
    }
}