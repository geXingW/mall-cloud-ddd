//package com.gexingw.mall.user.service.infra.dao.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//@Configuration
//@EnableWebMvc
//@RequiredArgsConstructor(onConstructor_ = {@Lazy})
//public class ResourceServerConfig2 {
//
//    private final CustomOpaqueTokenIntrospector opaqueTokenIntrospector;
////    private final AuthenticationEntryPoint authenticationEntryPointHandler;
////    private final AccessDeniedHandler accessDeniedHandler;
////    private final AuthenticationFilter authenticationFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // 禁用CSRF
//        http.csrf(AbstractHttpConfigurer::disable);
//
//        // 无状态Session
//        http.sessionManagement(
//                sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        );
//
//        // 配置权限
//        http.authorizeHttpRequests(httpRequests -> httpRequests
//                .requestMatchers("/public/**", "/actuator/**").permitAll()
//                .anyRequest().authenticated()
//        );
//
////        http.authorizeRequests()
////                // 公开接口
////                .antMatchers("/public/**", "/actuator/**").permitAll()
////                // 其他接口需要认证
////                .anyRequest().authenticated();
//
//        // 配置OAuth2资源服务器，使用不透明令牌
//        http.oauth2ResourceServer(
//                resourceServer -> resourceServer.opaqueToken(
//                        opaqueToken -> opaqueToken.introspector(opaqueTokenIntrospector)
//                )
//        );
////                .opaqueToken()
////                .introspector(opaqueTokenIntrospector);
//
//        // 添加认证过滤器
////        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        // 认证异常处理
//        http.exceptionHandling(
//                exceptionHandling -> exceptionHandling
//                        .authenticationEntryPoint(new AuthenticationEntryPointHandler())
//                        .accessDeniedHandler(new AccessDeniedHandler())
//        );
//
////        http.exceptionHandling()
////                .authenticationEntryPoint(authenticationEntryPointHandler)
////                .accessDeniedHandler(accessDeniedHandler);
//
//        return http.build();
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public CustomOpaqueTokenAuthenticationConverter opaqueTokenAuthenticationConverter() {
//        return new CustomOpaqueTokenAuthenticationConverter();
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public AuthenticationFilter authenticationFilter() {
//        return new AuthenticationFilter();
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public AuthenticationEntryPoint authenticationEntryPoint() {
//        return new AuthenticationEntryPointHandler();
//    }
//
//}
