//package com.gexingw.mall.auth.service.infra.support.auth;
//
//import com.gexingw.mall.auth.service.infra.support.auth.filter.ServletRequestJsonParamsWrapperFilter;
//import com.gexingw.mall.infrastucture.core.constant.PasswordConstant;
//import com.nimbusds.jose.jwk.source.JWKSource;
//import com.nimbusds.jose.proc.SecurityContext;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//import org.springframework.security.oauth2.core.OAuth2Token;
//import org.springframework.security.oauth2.core.oidc.OidcScopes;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
//import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
//import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
//import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
//import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
//import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
//import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
//import org.springframework.security.oauth2.server.authorization.token.*;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import javax.sql.DataSource;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * mall-user-service
// *
// * @author GeXingW
// * @date 2024/2/17 9:01
// */
//@Configuration
//@RequiredArgsConstructor(onConstructor_ = {@Lazy})
//public class AuthorizationServerConfiguration2 {
//
//    //    private final PasswordEncoder passwordEncoder;
//    private final ServletRequestJsonParamsWrapperFilter servletRequestJsonParamsWrapperFilter;
//
//    @Bean
//    @SneakyThrows
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SecurityFilterChain authorizationFilterChain(HttpSecurity httpSecurity) {
//
//        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
//
//        return httpSecurity.exceptionHandling(
//                exceptions -> exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
//        ).build();
//
//        /**
//        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
////        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationCode();
//
////        authorizationServerConfigurer
////                .tokenEndpoint(tokenEndpoint -> tokenEndpoint
////                        .errorResponseHandler( new AuthenticationFailureHandler())
////                        // 密码模式
////                        .accessTokenRequestConverter(new OAuth2PasswordAuthenticationConvert())
////                        .authenticationProvider(new OAuth2PasswordAuthenticationProvider())
////                )
////                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint.errorResponseHandler(new AuthenticationFailureHandler()))
////                .clientAuthentication(
////                        clientAuthentication -> clientAuthentication.errorResponseHandler(new AuthenticationFailureHandler())
////                );
//
//        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
////        httpSecurity.requestMatcher(endpointsMatcher)
////                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
////                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher));
//
//        httpSecurity.securityMatcher(endpointsMatcher)
//                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
//                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher));
//
//
//        httpSecurity.exceptionHandling(exception -> exception.accessDeniedHandler(new AccessDeniedHandler()));
//        httpSecurity.with(authorizationServerConfigurer, (authorizationServer) -> {
//            authorizationServer.tokenEndpoint(tokenEndpoint -> tokenEndpoint
//                            .errorResponseHandler(new AuthenticationFailureHandler())
//                            // 密码模式
//                            .accessTokenRequestConverter(new OAuth2PasswordAuthenticationConvert())
//                            .authenticationProvider(new OAuth2PasswordAuthenticationProvider())
//                    )
//                    .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint.errorResponseHandler(new AuthenticationFailureHandler()))
//                    .clientAuthentication(
//                            clientAuthentication -> clientAuthentication.errorResponseHandler(new AuthenticationFailureHandler())
//                    )
//                    .oidc(Customizer.withDefaults());
//        });
//        httpSecurity.addFilterBefore(servletRequestJsonParamsWrapperFilter, ChannelProcessingFilter.class);
//
//        return httpSecurity.build();
//         **/
//    }
//
//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }
//
//    /**
//     * 定义Token生成器
//     * ClientCredentials模式依赖 {@link JwtGenerator}
//     * AuthorizationCode模式依赖 {@link OAuth2AccessTokenGenerator}
//     * RefreshToken模式依赖 {@link OAuth2RefreshTokenGenerator}
//     *
//     * @param jwtEncoder JWT模式需要一个Encoder
//     * @return {@link OAuth2TokenGenerator}
//     */
//    @Bean
//    public OAuth2TokenGenerator<OAuth2Token> oAuth2TokenGenerator(JwtEncoder jwtEncoder) {
//        return new DelegatingOAuth2TokenGenerator(
//                new OAuth2AccessTokenGenerator(), new OAuth2RefreshTokenGenerator(), new JwtGenerator(jwtEncoder));
//    }
//
//    @Bean
//    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
//        return new NimbusJwtEncoder(jwkSource);
//    }
//
//    @Bean
//    public AuthorizationServerSettings authorizationServerSettings() {
//        return AuthorizationServerSettings.builder().build();
//    }
//
//    @Bean
//    public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
//        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
//    }
//
//    @Bean
//    public JdbcRegisteredClientRepository jdbcRegisteredClientRepository(JdbcTemplate jdbcTemplate) {
//        RegisteredClient messagingClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("messaging-client")
//                .clientSecret("{noop}secret")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
//                .redirectUri("http://127.0.0.1:8080/authorized")
//                .postLogoutRedirectUri("http://127.0.0.1:8080/logged-out")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
//                .scope("message.read")
//                .scope("message.write")
//                .scope("user.read")
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//                .build();
//
//        RegisteredClient deviceClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("device-messaging-client")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
//                .authorizationGrantType(AuthorizationGrantType.DEVICE_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .scope("message.read")
//                .scope("message.write")
//                .build();
//
//        RegisteredClient tokenExchangeClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("token-client")
//                .clientSecret("{noop}token")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(new AuthorizationGrantType("urn:ietf:params:oauth:grant-type:token-exchange"))
//                .scope("message.read")
//                .scope("message.write")
//                .build();
//
//        RegisteredClient mtlsDemoClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("mtls-demo-client")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.TLS_CLIENT_AUTH)
//                .clientAuthenticationMethod(ClientAuthenticationMethod.SELF_SIGNED_TLS_CLIENT_AUTH)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .scope("message.read")
//                .scope("message.write")
//                .clientSettings(
//                        ClientSettings.builder()
//                                .x509CertificateSubjectDN("CN=demo-client-sample,OU=Spring Samples,O=Spring,C=US")
//                                .jwkSetUrl("http://127.0.0.1:8080/jwks")
//                                .build()
//                )
//                .tokenSettings(
//                        TokenSettings.builder()
//                                .x509CertificateBoundAccessTokens(true)
//                                .build()
//                )
//                .build();
//
//        // Save registered client's in db as if in-memory
//        JdbcRegisteredClientRepository registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
////        registeredClientRepository.save(messagingClient);
////        registeredClientRepository.save(deviceClient);
////        registeredClientRepository.save(tokenExchangeClient);
////        registeredClientRepository.save(mtlsDemoClient);
//
//        return registeredClientRepository;
//    }
//
////    @Bean
////    public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
////        JdbcOAuth2AuthorizationService authorizationService = new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
////
////        JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper rowMapper = new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(registeredClientRepository);
////
////        ObjectMapper objectMapper = new ObjectMapper();
////        ClassLoader classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();
////
////        // 1. 注册 Spring Security 的核心模块
////        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
////        objectMapper.registerModules(securityModules);
////
////        // 2. 注册官方 OAuth2 授权服务模块（处理 TokenSettings 等标准类）
////        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
////
////        // 3. 注册自定义模块（处理 AuthInfo 等项目特定类）
////        objectMapper.registerModule(new OAuth2AuthorizationJackson2Module());
////
////        rowMapper.setObjectMapper(objectMapper);
////        authorizationService.setAuthorizationRowMapper(rowMapper);
////
////        return authorizationService;
////    }
//
////    @Bean
////    public OAuth2AuthorizationService oAuth2AuthorizationService(
////            RedisTemplate<Object, Object> restTemplate, @Qualifier("javaSerializerRedisTemplate") RedisTemplate<String, Object> javaSerializerRedisTemplate
////    ) {
////        return new RedisOAuth2AuthorizationService(restTemplate, javaSerializerRedisTemplate);
////    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
////        Map<String, PasswordEncoder> encoders = Collections.singletonMap(
////                PasswordConstant.BCRYPT_ENCODER_ID, new BCryptPasswordEncoder()
////        );
////        return new DelegatingPasswordEncoder(PasswordConstant.BCRYPT_ENCODER_ID, encoders);
//
//        //noinspection deprecation
//        Map<String, PasswordEncoder> encoderMap = Map.of(
//                PasswordConstant.BCRYPT_ENCODER_ID, new BCryptPasswordEncoder(),
//                PasswordConstant.NOOP_ENCODER_ID, NoOpPasswordEncoder.getInstance()
//        );
//
//        return new DelegatingPasswordEncoder(PasswordConstant.BCRYPT_ENCODER_ID, encoderMap);
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true); // 允许发送 Cookie
//
//        List<String> allowedOrigins = Arrays.asList(
//                "http://localhost:4200", "http://localhost:3001", "http://localhost:3002", "http://localhost:3003", "http://192.168.0.103:3000"
//        );
//        config.setAllowedOriginPatterns(allowedOrigins); // 设置允许的来源
//
//        config.addAllowedHeader("*"); // 允许所有请求头
//        config.addAllowedMethod("*"); // 允许所有 HTTP 方法
//        source.registerCorsConfiguration("/**", config); // 对所有路径生效
//        return source;
//    }
//
//}
