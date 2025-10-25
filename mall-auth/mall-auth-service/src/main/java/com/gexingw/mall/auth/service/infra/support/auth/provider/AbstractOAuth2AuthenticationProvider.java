package com.gexingw.mall.auth.service.infra.support.auth.provider;

import com.gexingw.mall.auth.service.infra.dao.authuser.po.AuthUserPO;
import com.gexingw.mall.auth.service.infra.dao.regsteredclient.po.RegisteredClientPO;
import com.gexingw.mall.infrastructure.util.SpringUtil;
import com.gexingw.mall.infrastucture.core.domain.AuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

/**
 * mall-user-service
 *
 * @author GeXingW
 * @date 2024/2/16 17:52
 */
@Slf4j
public abstract class AbstractOAuth2AuthenticationProvider implements AuthenticationProvider {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AbstractOAuth2AuthenticationProvider.class);

    private volatile PasswordEncoder passwordEncoder;

    /**
     * 构建一个AuthInfo信息
     *
     * @param authUserPO       用户信息
     * @param registeredClient 客户端信息
     * @return AuthInfo
     */
    public AuthInfo buildAuthInfo(AuthUserPO authUserPO, RegisteredClientPO registeredClient) {
        Long clientId = registeredClient.getId();
        AuthInfo.User user = new AuthInfo.User(authUserPO.getId(), authUserPO.getUsername(), authUserPO.getPhone());
        AuthInfo.Client client = new AuthInfo.Client(clientId, registeredClient.getClientId(), new ArrayList<>());

        return new AuthInfo(user, client);
    }

    public Boolean passwordVerify(String rawPassword, String encodePassword) {
        if (passwordEncoder == null) {
            synchronized (this) {
                if (passwordEncoder == null) {
                    passwordEncoder = SpringUtil.getBean(PasswordEncoder.class);
                }
            }
        }

        return passwordEncoder.matches(rawPassword, encodePassword);
    }

    @Override
    public abstract boolean supports(Class<?> authentication);

    public abstract String grantType();

    public abstract Authentication getAuthentication(Authentication authentication, RegisteredClientPO registeredClient);

}
