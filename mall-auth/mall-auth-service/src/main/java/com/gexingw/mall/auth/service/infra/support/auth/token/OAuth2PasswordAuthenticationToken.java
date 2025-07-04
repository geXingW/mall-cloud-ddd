package com.gexingw.mall.auth.service.infra.support.auth.token;

import com.gexingw.mall.auth.service.infra.support.auth.provider.OAuth2PasswordAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;

/**
 * mall-user-service
 *
 * @author GeXingW
 * @date 2024/2/16 18:01
 */
public class OAuth2PasswordAuthenticationToken extends AbstractOAuth2AuthenticationToken {

    public OAuth2PasswordAuthenticationToken(Authentication clientPrincipal, Map<String, Object> additionalParameters) {
        super(new AuthorizationGrantType(OAuth2PasswordAuthenticationProvider.GRANT_TYPE), clientPrincipal, additionalParameters);
    }

}
