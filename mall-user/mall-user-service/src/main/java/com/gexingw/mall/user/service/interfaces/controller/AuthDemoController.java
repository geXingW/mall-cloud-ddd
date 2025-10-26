package com.gexingw.mall.user.service.interfaces.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 演示如何获取OpaqueToken响应的控制器
 *
 * @author GeXingW
 */
@Slf4j
@RestController
@RequestMapping("/auth-demo")
@RequiredArgsConstructor
public class AuthDemoController {

    /**
     * 获取当前认证用户的令牌信息，包括原始响应
     *
     * @param principal 认证主体
     * @return 令牌信息
     */
    @GetMapping("/token-info")
    public Map<String, Object> getTokenInfo(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        Map<String, Object> result = new HashMap<>();
        
        // 获取基本信息
        result.put("name", principal.getName());
        result.put("authorities", principal.getAuthorities());
        
        // 获取属性信息
        Map<String, Object> attributes = principal.getAttributes();
        result.put("attributes", attributes);
        
        // 获取原始响应（如果存在）
        if (attributes.containsKey("raw_response")) {
            result.put("raw_response", attributes.get("raw_response"));
        }
        
        // 记录日志
        log.info("Token info: {}", result);
        
        return result;
    }
}
