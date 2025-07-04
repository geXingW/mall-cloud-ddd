package com.gexingw.mall.auth.service.infra.support.auth.token;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Map;

/**
 * mall-user-service
 *
 * @author GeXingW
 * @date 2024/2/16 19:42
 */
public abstract class AbstractOAuth2AuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    protected AbstractOAuth2AuthenticationToken(AuthorizationGrantType authorizationGrantType, Authentication clientPrincipal, Map<String, Object> additionalParameters) {
        super(authorizationGrantType, clientPrincipal, additionalParameters);
    }

    public Object getAdditionalParams(String name) {
        Map<String, Object> parameters = this.getAdditionalParameters();

        return parameters.get(name);
    }

}
