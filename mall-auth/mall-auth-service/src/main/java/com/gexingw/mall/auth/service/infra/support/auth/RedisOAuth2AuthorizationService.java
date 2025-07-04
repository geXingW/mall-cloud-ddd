package com.gexingw.mall.auth.service.infra.support.auth;

import cn.hutool.core.lang.Assert;
import com.gexingw.mall.infrastructure.redis.RedisUtil;
import com.gexingw.mall.infrastucture.core.constant.OAuth2Constant;
import com.gexingw.mall.infrastucture.core.domain.AuthInfo;
import com.gexingw.mall.infrastucture.core.enums.AuthRespCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.security.Principal;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;


/**
 * mall-user-service
 *
 * @author GeXingW
 * @date 2024/2/17 12:11
 */
//@Component
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final RedisTemplate<Object, Object> redisTemplate;
    private final RedisTemplate<String, Object> javaSerialRedisTemplate;

//    private final RedisSerializer<Object> valueSerializer;

    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "Authorization cannot be null.");
        javaSerialRedisTemplate.setValueSerializer(RedisSerializer.java());

        // 当前的秒数
        long curTimeSeconds = System.currentTimeMillis() / 1000;
        if (authorization.getAttribute(OAuth2ParameterNames.STATE) != null) {
            String token = authorization.getAttribute(OAuth2ParameterNames.STATE);
            javaSerialRedisTemplate.opsForValue().set(
                    String.format(OAuth2Constant.STATE_TOKEN_CACHE_NAME, token), authorization, Duration.ofMinutes(10).getSeconds()
            );
        }

        // AuthorizationCode
        if (authorization.getToken(OAuth2AuthorizationCode.class) != null) {
            OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization.getToken(OAuth2AuthorizationCode.class);
            if (authorizationCode != null) {
                OAuth2AuthorizationCode authorizationCodeToken = authorizationCode.getToken();
                long expireSeconds = Optional.ofNullable(authorizationCodeToken.getExpiresAt()).orElse(Instant.now()).getEpochSecond();

                javaSerialRedisTemplate.opsForValue().set(
                        String.format(OAuth2Constant.AUTHORIZATION_CODE_CACHE_NAME, authorizationCodeToken), authorization,
                        Duration.ofSeconds(expireSeconds, curTimeSeconds)
                );
            }
        }

        // RefreshToken
        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getRefreshToken();
        if (refreshToken != null) {
            String refreshTokenValue = refreshToken.getToken().getTokenValue();
            long refreshTokenExpiresSeconds = Optional.ofNullable(refreshToken.getToken().getExpiresAt()).orElse(Instant.now()).getEpochSecond();
            javaSerialRedisTemplate.opsForValue().set(
                    String.format(OAuth2Constant.REFRESH_TOKEN_CACHE_NAME, refreshTokenValue), authorization,
                    Duration.ofSeconds(refreshTokenExpiresSeconds - curTimeSeconds)
            );
        }

        // AccessToken
        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        if (accessToken != null) {
            String accessTokenValue = accessToken.getToken().getTokenValue();

            // Provider返回的authenticationToken
            UsernamePasswordAuthenticationToken authenticationToken = authorization.getAttribute(Principal.class.getName());
            if (authenticationToken == null) {
                throw new OAuth2AuthenticationException(
                        new OAuth2Error(AuthRespCode.SERVER_ERROR.getCode().toString()), AuthRespCode.SERVER_ERROR.getMessage()
                );
            }

            long accessTokenExpireSeconds = Optional.ofNullable(accessToken.getToken().getExpiresAt()).orElse(Instant.now()).getEpochSecond();
            Duration accessTokenExpireDuration = getDiffSeconds(accessTokenExpireSeconds, curTimeSeconds);
            javaSerialRedisTemplate.opsForValue().set(
                    String.format(OAuth2Constant.ACCESS_TOKEN_CACHE_NAME, accessTokenValue), authorization, accessTokenExpireDuration
            );

            // 当前认证用户的AuthInfo信息
            AuthInfo authInfo = (AuthInfo) authenticationToken.getPrincipal();
            if (authInfo != null) {
                RedisUtil.set(
                        String.format(OAuth2Constant.ACCESS_TOKEN_AUTH_INFO_CACHE_NAME, accessTokenValue), authInfo
                        , accessTokenExpireDuration.getSeconds()
                );
//                redisTemplate.opsForValue().set(
//                        String.format(OAuth2Constant.ACCESS_TOKEN_AUTH_INFO_CACHE_NAME, accessTokenValue), authInfo, accessTokenExpireDuration
//                );
            }
        }
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        org.springframework.util.Assert.notNull(authorization, "authorization cannot be null");

        if (authorization.getAttribute(OAuth2ParameterNames.STATE) != null) {
            String token = authorization.getAttribute(OAuth2ParameterNames.STATE);
            RedisUtil.del(String.format(OAuth2Constant.STATE_TOKEN_CACHE_NAME, token));
        }

        if (authorization.getToken(OAuth2AuthorizationCode.class) != null) {
            OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization.getToken(OAuth2AuthorizationCode.class);
            if (authorizationCode != null) {
                OAuth2AuthorizationCode authorizationCodeToken = authorizationCode.getToken();
                RedisUtil.del(String.format(OAuth2Constant.AUTHORIZATION_CODE_CACHE_NAME, authorizationCodeToken));
            }
        }

        TokenSettings tokenSettings = authorization.getAttribute(OAuth2Constant.TOKEN_SETTINGS);
        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getRefreshToken();
        if (refreshToken != null && tokenSettings != null) {
            RedisUtil.del(String.format(OAuth2Constant.REFRESH_TOKEN_CACHE_NAME, refreshToken.getToken().getTokenValue()));
        }

        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        if (accessToken != null) {
            String accessTokenValue = accessToken.getToken().getTokenValue();
            RedisUtil.del(String.format(OAuth2Constant.ACCESS_TOKEN_CACHE_NAME, accessTokenValue));
            RedisUtil.del(String.format(OAuth2Constant.ACCESS_TOKEN_AUTH_INFO_CACHE_NAME, accessTokenValue));
        }
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return null;
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        org.springframework.util.Assert.hasText(token, "token cannot be empty");
        org.springframework.util.Assert.notNull(tokenType, "tokenType cannot be empty");

        Object redisObject = javaSerialRedisTemplate.opsForValue().get(String.format(OAuth2Constant.ACCESS_TOKEN_CACHE_NAME, token));
        return (OAuth2Authorization) redisObject;
    }

    private Duration getDiffSeconds(long expireSeconds, long currentSeconds) {
        return Duration.ofSeconds(expireSeconds - currentSeconds);
    }

}
