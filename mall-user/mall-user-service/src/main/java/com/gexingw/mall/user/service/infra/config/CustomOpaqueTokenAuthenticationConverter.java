package com.gexingw.mall.user.service.infra.config;

import com.gexingw.mall.infrastucture.core.domain.AuthInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.*;

/**
 * 自定义透明令牌认证转换器
 * 将OAuth2认证信息转换为系统内部的Authentication对象
 *
 * @author GeXingW
 */
public class CustomOpaqueTokenAuthenticationConverter implements Converter<OAuth2AuthenticatedPrincipal, Authentication> {

    @Override
    public Authentication convert(OAuth2AuthenticatedPrincipal principal) {
        // 从OAuth2认证信息中提取用户信息
        Map<String, Object> attributes = principal.getAttributes();

        // 构建AuthInfo.User对象
        Long userId = Long.valueOf(attributes.getOrDefault("user_id", "0").toString());
        String username = attributes.getOrDefault("username", "").toString();
        String phone = attributes.getOrDefault("phone", "").toString();

        AuthInfo.User user = new AuthInfo.User(userId, username, phone);

        // 构建AuthInfo.Client对象
        Long clientId = Long.valueOf(attributes.getOrDefault("client_id", "0").toString());
        String clientIdStr = attributes.getOrDefault("client_id_str", "").toString();

        // 获取作用域
        List<String> scopes = new ArrayList<>();
        if (attributes.containsKey("scope")) {
            String scopeStr = attributes.get("scope").toString();
            if (scopeStr != null && !scopeStr.isEmpty()) {
                scopes.addAll(Set.of(scopeStr.split(" ")));
            }
        }

        AuthInfo.Client client = new AuthInfo.Client(clientId, clientIdStr, scopes);
        AuthInfo authInfo = new AuthInfo(user, client);

        // 创建Authentication对象
        Authentication authentication = new Authentication(authInfo);
        authentication.setAuthenticated(true);

        return authentication;
    }
}
