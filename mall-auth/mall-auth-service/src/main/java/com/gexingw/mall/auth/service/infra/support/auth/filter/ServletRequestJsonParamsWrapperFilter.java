package com.gexingw.mall.auth.service.infra.support.auth.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.gexingw.mall.infrastucture.core.util.IoUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

/**
 * mall-user-service
 *
 * @author GeXingW
 * @date 2024/2/17 11:51
 */
@Component
public class ServletRequestJsonParamsWrapperFilter extends OncePerRequestFilter {

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String contentType = Optional.ofNullable(request.getContentType()).orElse("");
        if (!contentType.equals(MediaType.APPLICATION_JSON_VALUE) || !"/oauth2/token".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        ServletInputStream inputStream = request.getInputStream();
        if (inputStream == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String jsonStr = IoUtil.toString(inputStream, StandardCharsets.UTF_8.toString());
        if (StringUtils.isBlank(jsonStr)) {
            filterChain.doFilter(request, response);
            return;
        }

        TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {
        };
        Map<String, Object> jsonParameters = JSON.parseObject(jsonStr, typeReference);
        ServletRequestJsonParamsWrapper jsonParamsWrapper = new ServletRequestJsonParamsWrapper(request);
        jsonParamsWrapper.setParams(jsonParameters);

        filterChain.doFilter(jsonParamsWrapper, response);

    }

}
