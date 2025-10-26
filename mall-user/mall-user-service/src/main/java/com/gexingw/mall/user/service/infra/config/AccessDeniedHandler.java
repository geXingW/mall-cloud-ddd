package com.gexingw.mall.user.service.infra.config;

import com.gexingw.mall.infrastructure.util.ResponseUtil;
import com.gexingw.mall.infrastucture.core.enums.CommonRespCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;

/**
 * mall-user-service
 *
 * @author GeXingW
 * @date 2024/2/17 11:09
 */
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);

        ResponseUtil.jsonResponse(httpResponse, CommonRespCode.FORBIDDEN);
    }

}
