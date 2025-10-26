package com.gexingw.mall.user.service.infra.config;

import com.gexingw.mall.infrastructure.util.ResponseUtil;
import com.gexingw.mall.infrastucture.core.enums.CommonRespCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * mall-user-service
 *
 * @author GeXingW
 * @date 2024/2/17 10:46
 */
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        ResponseUtil.jsonResponse(httpResponse, CommonRespCode.UNAUTHORIZED);
    }

}
