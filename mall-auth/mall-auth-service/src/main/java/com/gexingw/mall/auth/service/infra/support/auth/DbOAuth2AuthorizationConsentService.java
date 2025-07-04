package com.gexingw.mall.auth.service.infra.support.auth;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;

/**
 * mall-user-service
 *
 * @author GeXingW
 * @date 2024/2/17 12:15
 */
@Component
public class DbOAuth2AuthorizationConsentService extends JdbcOAuth2AuthorizationConsentService {

    public DbOAuth2AuthorizationConsentService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
        super(jdbcOperations, registeredClientRepository);
    }

}
