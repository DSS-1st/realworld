package com.dss.realworld.common.config;

import com.dss.realworld.common.dto.SecurityResponse;
import com.dss.realworld.common.jwt.JwtAuthenticationFilter;
import com.dss.realworld.common.jwt.JwtAuthorizationFilter;
import com.dss.realworld.common.jwt.JwtProcessor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final JwtProcessor jwtProcessor;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("디버그: BCryptPasswordEncoder Bean 등록완료");

        return new BCryptPasswordEncoder();
    }

    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {

        @Override
        public void configure(final HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager, jwtProcessor));
            builder.addFilter(new JwtAuthorizationFilter(authenticationManager, jwtProcessor));
            super.configure(builder);
        }
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        log.debug("디버그: SecurityFilterChain 빈 등록완료");

        http.httpBasic().disable();
        http.formLogin().disable();
        http.headers().frameOptions().disable();

        http.csrf().disable();
        http.cors().configurationSource(configurationSource());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.apply(new CustomSecurityFilterManager());

        http.exceptionHandling().authenticationEntryPoint((request, response, authenticationException) -> {
            SecurityResponse.fail(response, "로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        });

        http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
            SecurityResponse.fail(response, "권한이 없습니다.", HttpStatus.FORBIDDEN);
        });

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/users", "/api/users/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/profiles/**", "/api/articles/**", "/api/tags").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        log.debug("디버그: CorsConfigurationSource 설정이 SecurityFilterChain에 등록완료");

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("http://localhost:8081");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("Authorization");
        configuration.setAllowedMethods(List.of("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}