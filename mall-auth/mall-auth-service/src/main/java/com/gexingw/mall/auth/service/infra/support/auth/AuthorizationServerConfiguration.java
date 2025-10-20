package com.gexingw.mall.auth.service.infra.support.auth;

import com.gexingw.mall.auth.service.infra.support.auth.convert.OAuth2PasswordAuthenticationConvert;
import com.gexingw.mall.auth.service.infra.support.auth.filter.ServletRequestJsonParamsWrapperFilter;
import com.gexingw.mall.auth.service.infra.support.auth.provider.OAuth2PasswordAuthenticationProvider;
import com.gexingw.mall.infrastucture.core.constant.PasswordConstant;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * mall-user-service
 *
 * @author GeXingW
 * @date 2024/2/17 9:01
 */
@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class AuthorizationServerConfiguration {

    //    private final PasswordEncoder passwordEncoder;
    private final ServletRequestJsonParamsWrapperFilter servletRequestJsonParamsWrapperFilter;

    @Bean
    @SneakyThrows
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationFilterChain(HttpSecurity httpSecurity) {

//        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
//
//        return httpSecurity.exceptionHandling(
//                exceptions -> exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
//        ).build();
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
//        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationCode();

//        authorizationServerConfigurer
//                .tokenEndpoint(tokenEndpoint -> tokenEndpoint
//                        .errorResponseHandler( new AuthenticationFailureHandler())
//                        // 密码模式
//                        .accessTokenRequestConverter(new OAuth2PasswordAuthenticationConvert())
//                        .authenticationProvider(new OAuth2PasswordAuthenticationProvider())
//                )
//                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint.errorResponseHandler(new AuthenticationFailureHandler()))
//                .clientAuthentication(
//                        clientAuthentication -> clientAuthentication.errorResponseHandler(new AuthenticationFailureHandler())
//                );

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
//        httpSecurity.requestMatcher(endpointsMatcher)
//                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
//                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher));

        httpSecurity.securityMatcher(endpointsMatcher)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher));


        httpSecurity.exceptionHandling(exception -> exception.accessDeniedHandler(new AccessDeniedHandler()));
        httpSecurity.with(authorizationServerConfigurer, (authorizationServer) -> {
            authorizationServer.tokenEndpoint(tokenEndpoint -> tokenEndpoint
                            .errorResponseHandler(new AuthenticationFailureHandler())
                            // 密码模式
                            .accessTokenRequestConverter(new OAuth2PasswordAuthenticationConvert())
                            .authenticationProvider(new OAuth2PasswordAuthenticationProvider())
                    )
                    .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint.errorResponseHandler(new AuthenticationFailureHandler()))
                    .clientAuthentication(
                            clientAuthentication -> clientAuthentication.errorResponseHandler(new AuthenticationFailureHandler())
                    )
                    .oidc(Customizer.withDefaults());
        });
        httpSecurity.addFilterBefore(servletRequestJsonParamsWrapperFilter, ChannelProcessingFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().issuer("http://127.0.0.1:8090").build();
    }

    @Bean
    public OAuth2TokenGenerator<OAuth2Token> oAuth2TokenGenerator() {
        return new DelegatingOAuth2TokenGenerator(
                new OAuth2AccessTokenGenerator(), new OAuth2RefreshTokenGenerator());
    }

}
