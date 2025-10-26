package com.gexingw.mall.auth.service.infra.support.auth.provider;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gexingw.mall.auth.service.infra.dao.authuser.mapper.AuthUserMapper;
import com.gexingw.mall.auth.service.infra.dao.authuser.po.AuthUserPO;
import com.gexingw.mall.auth.service.infra.dao.regsteredclient.mapper.RegisteredClientMapper;
import com.gexingw.mall.auth.service.infra.dao.regsteredclient.po.RegisteredClientPO;
import com.gexingw.mall.auth.service.infra.support.auth.token.OAuth2PasswordAuthenticationToken;
import com.gexingw.mall.auth.service.infra.support.constant.ParameterConstant;
import com.gexingw.mall.auth.service.infra.support.util.OAuth2AuthenticationProviderUtils;
import com.gexingw.mall.infrastructure.util.SpringUtil;
import com.gexingw.mall.infrastucture.core.constant.OAuth2Constant;
import com.gexingw.mall.infrastucture.core.domain.AuthInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;

import static com.gexingw.mall.infrastucture.core.enums.AuthRespCode.USERNAME_PASSWD_INVALID;
import static com.gexingw.mall.infrastucture.core.enums.CommonRespCode.PARAMS_INVALID;


/**
 * mall-user-service
 *
 * @author GeXingW
 * @date 2024/2/16 17:51
 */
public class OAuth2PasswordAuthenticationProvider extends AbstractOAuth2AuthenticationProvider {

    public static final String GRANT_TYPE = "password";

    public final AuthUserMapper authUserMapper = SpringUtil.getBean(AuthUserMapper.class);

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2PasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public String grantType() {
        return GRANT_TYPE;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2PasswordAuthenticationToken authenticationToken = (OAuth2PasswordAuthenticationToken) authentication;
        OAuth2ClientAuthenticationToken clientPrincipal = OAuth2AuthenticationProviderUtils.getAuthenticatedClientElseThrowInvalidClient(authenticationToken);

        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        Assert.notNull(registeredClient, "RegisteredClientModel should not be null!");

        Long registeredClientId = Long.valueOf(registeredClient.getId());
        RegisteredClientPO registeredClientPO = SpringUtil.getBean(RegisteredClientMapper.class).selectById(registeredClientId);
        Authentication userAuthentication = this.getAuthentication(authentication, registeredClientPO);

        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .principalName(authentication.getName())
                .attribute(Principal.class.getName(), userAuthentication)
                .authorizedScopes(authenticationToken.getScopes())
                .attribute(OAuth2Constant.TOKEN_SETTINGS, registeredClient.getTokenSettings())
                .authorizationGrantType(new AuthorizationGrantType(this.grantType()))
//                .accessToken(oAuth2AccessToken)
                ;
        OAuth2Authorization oAuth2Authorization = authorizationBuilder.build();

        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(clientPrincipal)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(authenticationToken.getScopes())
                .tokenType(OAuth2TokenType.ACCESS_TOKEN)
                .authorizationGrantType(new AuthorizationGrantType(GRANT_TYPE))
                .authorizationGrant(authenticationToken)
                ;


        // AccessToken
        DefaultOAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        //noinspection unchecked
        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = SpringUtil.getBean(OAuth2TokenGenerator.class);
        OAuth2Token generatedAccessToken = tokenGenerator.generate(tokenContext);
        Assert.notNull(generatedAccessToken, "AccessToken should not be null!");

        OAuth2AccessToken accessToken = OAuth2AuthenticationProviderUtils.accessToken(
                authorizationBuilder, generatedAccessToken, tokenContext
        );

        // 配置refreshToken，生成RefreshToken
        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN)) {
            DefaultOAuth2TokenContext.builder().registeredClient(registeredClient);

            tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();

            OAuth2Token generateRefreshToken = tokenGenerator.generate(tokenContext);
            refreshToken = (OAuth2RefreshToken) generateRefreshToken;
            authorizationBuilder.refreshToken(refreshToken);
        }

        OAuth2Authorization authorization = authorizationBuilder.build();

        OAuth2AuthorizationService authorizationService = SpringUtil.getBean(OAuth2AuthorizationService.class);
        Assert.notNull(authorizationService, "OAuth2AuthorizationService should not be null!");

        authorizationService.save(authorization);

        SecurityContextHolder.clearContext();

        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken, refreshToken);
    }

    public Authentication getAuthentication(Authentication authentication, RegisteredClientPO registeredClient) {
        OAuth2AuthorizationGrantAuthenticationToken passwordAuthentication = (OAuth2AuthorizationGrantAuthenticationToken) authentication;

        Map<String, Object> additionalParameters = passwordAuthentication.getAdditionalParameters();

        // 获取用户名
        String username = additionalParameters.get(ParameterConstant.USERNAME).toString();
        // 获取密码
        String password = additionalParameters.get(ParameterConstant.PASSWORD).toString();
        if (StringUtils.isAnyBlank(username, password)) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error(PARAMS_INVALID.getCode().toString()), PARAMS_INVALID.getMessage()
            );
        }

        // 本次登录的用户
        AuthUserPO authUser = authUserMapper.selectOne(new LambdaQueryWrapper<AuthUserPO>().eq(AuthUserPO::getUsername, username));
        if (authUser == null || !passwordVerify(password, authUser.getPassword())) {
            // 用户不存在
            throw new OAuth2AuthenticationException(
                    new OAuth2Error(USERNAME_PASSWD_INVALID.getSubCode().toString()), USERNAME_PASSWD_INVALID.getMessage()
            );
        }

        // 认证信息
        AuthInfo authInfo = this.buildAuthInfo(authUser, registeredClient);

        return new UsernamePasswordAuthenticationToken(authInfo, password, new ArrayList<>());
    }

}
