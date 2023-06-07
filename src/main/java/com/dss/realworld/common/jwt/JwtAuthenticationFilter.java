package com.dss.realworld.common.jwt;

import com.dss.realworld.common.auth.LoginUser;
import com.dss.realworld.common.dto.SecurityResponse;
import com.dss.realworld.user.api.dto.LoginRequestDto;
import com.dss.realworld.user.api.dto.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final AuthenticationManager authenticationManager;
    private final JwtProcessor jwtProcessor;

    public JwtAuthenticationFilter(final AuthenticationManager authenticationManager, final JwtProcessor jwtProcessor) {
        super(authenticationManager);
        super.setFilterProcessesUrl("/api/users/login");

        this.authenticationManager = authenticationManager;
        this.jwtProcessor = jwtProcessor;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException {
        log.debug("디버그: attemptAuthentication() 호출됨");

        try {
            ObjectMapper om = new ObjectMapper();
            LoginRequestDto loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            //└>JWT 생성이 아닌 사용자 인증을 위한 토큰 생성

            return authenticationManager.authenticate(authenticationToken); //LoginService.loadUserByUsername() 호출
        } catch (Exception e) {
            //아래 예외 발생 후 unsuccessfulAuthentication() 호출됨
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    //로그인 실패 시 작동
    @Override
    protected void unsuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException failed) throws IOException {
        SecurityResponse.fail(response, "로그인 실패", HttpStatus.UNAUTHORIZED);
    }

    //로그인 성공 시 작동
    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) {
        log.debug("디버그: successfulAuthentication() 호출됨");

        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        String jwtToken = jwtProcessor.create(loginUser);
        response.addHeader(JwtVO.HEADER, jwtToken);

        UserResponseDto userResponseDto = new UserResponseDto(loginUser, jwtToken);
        SecurityResponse.success(response, userResponseDto);
    }
}