package com.gexingw.mall.auth.service.infra.support.auth.convert;

import com.gexingw.mall.auth.service.infra.support.auth.provider.OAuth2PasswordAuthenticationProvider;
import com.gexingw.mall.auth.service.infra.support.auth.token.OAuth2PasswordAuthenticationToken;
import com.gexingw.mall.auth.service.infra.support.util.OAuth2EndpointUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.gexingw.mall.infrastucture.core.enums.CommonRespCode.PARAMS_INVALID;

/**
 * mall-user-service
 *
 * @author GeXingW
 * @date 2024/2/16 22:26
 */
public class OAuth2PasswordAuthenticationConvert extends AbstractAuthenticationConvert{

    @Nullable
    @Override
    public Authentication convert(HttpServletRequest request) {
        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getFormParameters(request);

        // grant_type (REQUIRED)
        String grantType = parameters.getFirst(OAuth2ParameterNames.GRANT_TYPE);
        if (!OAuth2PasswordAuthenticationProvider.GRANT_TYPE.equals(grantType)) {
            return null;
        }

        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();

        String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
        if (StringUtils.hasText(scope) && parameters.get(OAuth2ParameterNames.SCOPE).size() != 1) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error(PARAMS_INVALID.getCode().toString()), "Scope must be a single value"
            );
        }

        Set<String> requestedScopes = null;
        if (StringUtils.hasText(scope)) {
            requestedScopes = new HashSet<>(Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
        }

        Map<String, Object> additionalParameters = new HashMap<>();
        parameters.forEach((key, value) -> {
            if (!key.equals(OAuth2ParameterNames.GRANT_TYPE) && !key.equals(OAuth2ParameterNames.SCOPE)) {
                additionalParameters.put(key, (value.size() == 1) ? value.get(0) : value.toArray(new String[0]));
            }
        });

        return new OAuth2PasswordAuthenticationToken(clientPrincipal, requestedScopes, additionalParameters);
    }

}
