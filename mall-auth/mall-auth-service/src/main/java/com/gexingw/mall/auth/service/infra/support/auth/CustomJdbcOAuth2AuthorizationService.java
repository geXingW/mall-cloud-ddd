package com.gexingw.mall.auth.service.infra.support.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.util.List;

/**
 * 自定义 JdbcOAuth2AuthorizationService，使用自定义的 ObjectMapper 配置
 *
 * @author GeXingW
 */
public class CustomJdbcOAuth2AuthorizationService extends JdbcOAuth2AuthorizationService {

    /**
     * 构造函数
     *
     * @param jdbcTemplate            JDBC模板
     * @param registeredClientRepository 客户端仓库
     * @param objectMapper            自定义的ObjectMapper
     */
    public CustomJdbcOAuth2AuthorizationService(JdbcTemplate jdbcTemplate, 
                                               RegisteredClientRepository registeredClientRepository,
                                               ObjectMapper objectMapper) {
        super(jdbcTemplate, registeredClientRepository);
        
        // 配置 RowMapper
        OAuth2AuthorizationRowMapper authorizationRowMapper = new OAuth2AuthorizationRowMapper(registeredClientRepository);
        authorizationRowMapper.setLobHandler(new DefaultLobHandler());
        
        // 确保 ObjectMapper 配置正确
        authorizationRowMapper.setObjectMapper(objectMapper);
        
        // 设置 RowMapper
        setAuthorizationRowMapper(authorizationRowMapper);
    }
}
