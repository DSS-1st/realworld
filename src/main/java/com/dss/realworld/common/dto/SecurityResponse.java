package com.dss.realworld.common.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityResponse {

    private static final Logger log = LoggerFactory.getLogger(SecurityResponse.class);

    public static void fail(HttpServletResponse response, String message, HttpStatus httpStatus) throws IOException {
        String reasonPhrase = HttpStatus.UNAUTHORIZED.getReasonPhrase();
        if (httpStatus.value() == 403) reasonPhrase = HttpStatus.FORBIDDEN.getReasonPhrase();
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), reasonPhrase, message);

        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(errorResponse);

        response.setContentType("application/json; charset=utf-8");
        response.setStatus(httpStatus.value());
        response.getWriter().println(responseBody);
    }

    public static void success(HttpServletResponse response, Object dto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String responseBody = objectMapper.writeValueAsString(dto);

            response.setContentType("application/json; charset=utf-8");
            response.setStatus(201);
            response.getWriter().println(responseBody);
        } catch (IOException e) {
            log.error("서버 파싱 에러");
            throw new RuntimeException(e);
        }
    }
}