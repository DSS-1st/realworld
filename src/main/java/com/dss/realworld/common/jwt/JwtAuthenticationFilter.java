package com.dss.realworld.common.jwt;

import com.dss.realworld.common.auth.LoginUser;
import com.dss.realworld.common.dto.SecurityResponse;
import com.dss.realworld.user.api.dto.LoginUserRequestDto;
import com.dss.realworld.user.api.dto.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(final AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/users/login");
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException {
        log.debug("디버그: attemptAuthentication() 호출됨");

        try {
            ObjectMapper om = new ObjectMapper();
            LoginUserRequestDto loginUserRequestDto = om.readValue(request.getInputStream(), LoginUserRequestDto.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserRequestDto.getEmail(), loginUserRequestDto.getPassword());

// UserDetailsService.loadUserByUsername() 호출하여 강제 로그인, request, response되면 로그인 세션 종료됨
// 주석 처리된 다음의 권한체크 기능 사용을 위함
//            http.authorizeRequests()
//                    .antMatchers(HttpMethod.POST, "/api/users", "/api/users/login").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/profiles/**", "/api/articles/**", "/api/tags").permitAll()
//                    .anyRequest().authenticated();
            return authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            //unsuccessfulAuthentication() 호출
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    //로그인 실패 시 작동
    @Override
    protected void unsuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException failed) throws IOException, ServletException {
        SecurityResponse.unAuthentication(response, "로그인 실패");
    }

    //로그인 성공 시 작동
    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        log.debug("디버그: successfulAuthentication() 호출됨");

        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        String jwtToken = JwtProcess.create(loginUser);
        response.addHeader(JwtVO.HEADER, jwtToken);

        UserResponseDto userResponseDto = new UserResponseDto(loginUser, jwtToken);
        SecurityResponse.success(response, userResponseDto);
    }
}