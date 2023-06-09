package com.dss.realworld.common.jwt;

import com.dss.realworld.common.auth.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtProcessor jwtProcessor;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public JwtAuthorizationFilter(final AuthenticationManager authenticationManager, final JwtProcessor jwtProcessor) {
        super(authenticationManager);
        this.jwtProcessor = jwtProcessor;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
        log.debug("디버그: doFilterInternal() 호출 완료");

        if (isHeaderVerified(request, response)) {
            log.debug("디버그: token 존재 확인");

            String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
            LoginUser loginUser = jwtProcessor.verify(token);
            log.debug("디버그: token 검증 완료");

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser, "", Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("디버그: 임시 세션 생성 완료");
        }

        chain.doFilter(request, response);
    }

    private boolean isHeaderVerified(final HttpServletRequest request, final HttpServletResponse response) {
        String header = request.getHeader(JwtVO.HEADER);
        if (header == null || !header.startsWith(JwtVO.TOKEN_PREFIX)) {
            return false;
        } else {
            return true;
        }
    }
}