package com.gexingw.mall.user.service.infra.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 自定义透明令牌自省器
 * 负责与认证服务器通信，验证令牌有效性并获取令牌相关信息
 *
 * @author GeXingW
 */
@Slf4j
//@Component
public class CustomOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final OpaqueTokenIntrospector delegate;
    private final RestTemplate restTemplate;
    private final String introspectionUri;

    public CustomOpaqueTokenIntrospector(
            @Value("${spring.security.oauth2.resourceserver.opaquetoken.introspection-uri}") String introspectionUri,
            @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}") String clientId,
            @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}") String clientSecret) {
        this.introspectionUri = introspectionUri;

        // 创建RestTemplate用于手动调用令牌自省端点
        this.restTemplate = new RestTemplateBuilder()
                .basicAuthentication(clientId, clientSecret)
                .setConnectTimeout(Duration.ofSeconds(60))
                .setReadTimeout(Duration.ofSeconds(60))
                .build();

        // 保留原始的NimbusOpaqueTokenIntrospector作为委托
        this.delegate = new NimbusOpaqueTokenIntrospector(introspectionUri, clientId, clientSecret);
    }

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        try {
            // 获取原始响应
            Map<String, Object> rawResponse = getIntrospectionResponse(token);
            log.info("Token introspection raw response: {}", rawResponse);

            // 调用默认的令牌自省
            OAuth2AuthenticatedPrincipal principal = this.delegate.introspect(token);

            // 获取属性和权限
            Map<String, Object> attributes = principal.getAttributes();
            // 可以在这里添加从原始响应中获取的额外信息
            attributes.put("raw_response", rawResponse);

            Collection<GrantedAuthority> authorities = extractAuthorities(attributes);

            // 返回增强后的认证主体
            return new DefaultOAuth2AuthenticatedPrincipal(
                    principal.getName(), attributes, authorities);
        } catch (Exception e) {
            log.error("Token introspection failed", e);
            throw e;
        }
    }

    /**
     * 手动调用令牌自省端点获取原始响应
     */
    private Map<String, Object> getIntrospectionResponse(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("token", token);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
            // 使用exchange方法代替postForEntity，可以正确处理泛型响应
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    introspectionUri,
                    org.springframework.http.HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {});

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                log.warn("Token introspection returned non-200 status: {}", response.getStatusCode());
                return Map.of("error", "Invalid token introspection response");
            }
        } catch (Exception e) {
            log.error("Error getting token introspection response", e);
            return Map.of("error", e.getMessage());
        }
    }

    private Collection<GrantedAuthority> extractAuthorities(Map<String, Object> attributes) {
        // 从属性中提取权限信息
        if (attributes.containsKey("authorities")) {
            Object authoritiesObj = attributes.get("authorities");
            if (authoritiesObj instanceof List<?>) {
                List<?> authList = (List<?>) authoritiesObj;
                return authList.stream()
                        .filter(auth -> auth instanceof String)
                        .map(auth -> new SimpleGrantedAuthority((String) auth))
                        .collect(Collectors.toList());
            }
        }

        return new ArrayList<>();
    }
}
