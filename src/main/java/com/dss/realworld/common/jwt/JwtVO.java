package com.dss.realworld.common.jwt;

public interface JwtVO {

    String SECRET = "DSS01"; //todo 환경변수로 주입
    int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; //7일
    String TOKEN_PREFIX = "Token ";
    String HEADER = "Authorization";
}