package com.gexingw.mall.auth.service.infra.support.auth.token;

import com.gexingw.mall.auth.service.infra.support.auth.provider.OAuth2PasswordAuthenticationProvider;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * mall-user-service
 *
 * @author GeXingW
 * @date 2024/2/16 18:01
 */
@Getter
public class OAuth2PasswordAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    private final Set<String> scopes;

    /**
     * Constructs an {@code OAuth2PasswordAuthenticationToken} using the provided
     * parameters.
     *
     * @param clientPrincipal      the authenticated client principal
     * @param scopes               the requested scope(s)
     * @param additionalParameters the additional parameters
     */
    public OAuth2PasswordAuthenticationToken(
            Authentication clientPrincipal, @Nullable Set<String> scopes, @Nullable Map<String, Object> additionalParameters
    ) {
        super(new AuthorizationGrantType(OAuth2PasswordAuthenticationProvider.GRANT_TYPE), clientPrincipal, additionalParameters);

        this.scopes = Collections.unmodifiableSet((scopes != null) ? new HashSet<>(scopes) : Collections.emptySet());
    }

}
