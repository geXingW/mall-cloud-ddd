package com.gexingw.mall.auth.service.infra.support.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.util.List;

/**
 * Jackson 配置类，用于处理 OAuth2 相关类的序列化和反序列化
 *
 * @author W.sf
 * @date 2024/12/4 11:28
 */
@Configuration
public class JacksonConfiguration {

    /**
     * 配置通用 ObjectMapper
     */
    @Autowired
    public void configureObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
        SecurityJackson2Modules.enableDefaultTyping(objectMapper);
        objectMapper.registerModules(new CoreJackson2Module());
        objectMapper.registerModules(new OAuth2AuthorizationJackson2Module());
    }

    /**
     * 创建专用于 OAuth2 授权服务的 ObjectMapper
     */
//    @Bean
//    @Primary
//    public ObjectMapper oauth2ObjectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        // 注册标准模块
//        objectMapper.registerModule(new JavaTimeModule());
//
//        // 注册 Spring Security 模块
//        ClassLoader classLoader = getClass().getClassLoader();
//        List<com.fasterxml.jackson.databind.Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
//        objectMapper.registerModules(securityModules);
//
//        // 注册 OAuth2 授权服务器模块
//        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
//
//        // 注册自定义 OAuth2 模块
//        objectMapper.registerModule(new OAuth2AuthorizationJackson2Module());
//
//        // 添加 Mixin 类
//        objectMapper.addMixIn(TokenSettings.class, OAuth2AuthorizationJackson2Module.TokenSettingsMixin.class);
//        objectMapper.addMixIn(ClientSettings.class, OAuth2AuthorizationJackson2Module.ClientSettingsMixin.class);
//        objectMapper.addMixIn(OAuth2TokenFormat.class, OAuth2AuthorizationJackson2Module.OAuth2TokenFormatMixin.class);
//
//        // 启用默认类型
//        SecurityJackson2Modules.enableDefaultTyping(objectMapper);
//
//        return objectMapper;
//    }
}
