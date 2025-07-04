package com.gexingw.mall.user.service.infra.dao.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;

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
@Component
public class CustomOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final OpaqueTokenIntrospector delegate;

    public CustomOpaqueTokenIntrospector(
            @Value("${spring.security.oauth2.resourceserver.opaquetoken.introspection-uri}") String introspectionUri,
            @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}") String clientId,
            @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}") String clientSecret) {
        this.delegate = new NimbusOpaqueTokenIntrospector(introspectionUri, clientId, clientSecret);
    }

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        // 调用默认的令牌自省
        OAuth2AuthenticatedPrincipal principal = this.delegate.introspect(token);

        // 获取属性和权限
        Map<String, Object> attributes = principal.getAttributes();
        Collection<GrantedAuthority> authorities = extractAuthorities(attributes);

        // 返回增强后的认证主体
        return new DefaultOAuth2AuthenticatedPrincipal(
                principal.getName(), attributes, authorities);
    }

    private Collection<GrantedAuthority> extractAuthorities(Map<String, Object> attributes) {
        // 从属性中提取权限信息
        if (attributes.containsKey("authorities")) {
            List<String> authorities = (List<String>) attributes.get("authorities");
            return authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
