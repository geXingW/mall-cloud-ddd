package com.gexingw.mall.auth.service.infra.support.auth.provider;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gexingw.mall.auth.service.infra.dao.authuser.mapper.AuthUserMapper;
import com.gexingw.mall.auth.service.infra.dao.authuser.po.AuthUserPO;
import com.gexingw.mall.auth.service.infra.dao.regsteredclient.po.RegisteredClientPO;
import com.gexingw.mall.auth.service.infra.support.auth.token.OAuth2PasswordAuthenticationToken;
import com.gexingw.mall.auth.service.infra.support.constant.ParameterConstant;
import com.gexingw.mall.infrastructure.util.SpringUtil;
import com.gexingw.mall.infrastucture.core.domain.AuthInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

import java.util.ArrayList;

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
    public Authentication getAuthentication(Authentication authentication, RegisteredClientPO registeredClient) {
        OAuth2PasswordAuthenticationToken passwordAuthentication = (OAuth2PasswordAuthenticationToken) authentication;

        // 获取用户名
        String username = passwordAuthentication.getAdditionalParams(ParameterConstant.USERNAME).toString();
        // 获取密码
        String password = passwordAuthentication.getAdditionalParams(ParameterConstant.PASSWORD).toString();
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
