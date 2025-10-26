package com.gexingw.mall.user.service.infra.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.client.RestOperations;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.Duration;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class ResourceServerConfig {

    /**
     * 使用自定义的OpaqueTokenIntrospector来获取令牌自省响应
     * 注意：这里我们使用@Primary注解来确保使用我们的自定义实现
     *
     * @param customOpaqueTokenIntrospector 自定义的令牌自省器
     * @return OpaqueTokenIntrospector实例
     */
//    @Bean
//    @Primary
//    public OpaqueTokenIntrospector introspector(CustomOpaqueTokenIntrospector customOpaqueTokenIntrospector) {
//        return customOpaqueTokenIntrospector;
//    }


    /**
     * The resource server sets a connection and read timeout of 60 seconds to coordinate with the authorization server.
     *
     * @param builder
     * @param properties
     * @return
     */
    @Bean
    public OpaqueTokenIntrospector introspector(RestTemplateBuilder builder, OAuth2ResourceServerProperties properties) {
        RestOperations rest = builder
                .basicAuthentication(properties.getOpaquetoken().getClientId(), properties.getOpaquetoken().getClientSecret())
                .setConnectTimeout(Duration.ofSeconds(60))
                .setReadTimeout(Duration.ofSeconds(60))
                .build();

        return new NimbusOpaqueTokenIntrospector(properties.getOpaquetoken().getIntrospectionUri(), rest);
    }

}
