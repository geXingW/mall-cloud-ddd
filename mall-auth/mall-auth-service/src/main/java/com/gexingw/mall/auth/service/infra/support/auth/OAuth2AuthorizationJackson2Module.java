package com.gexingw.mall.auth.service.infra.support.auth;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gexingw.mall.infrastucture.core.domain.AuthInfo;

/**
 * 自定义Jackson Module，用于处理OAuth2相关类的序列化和反序列化
 *
 * @author GeXingW
 */
public class OAuth2AuthorizationJackson2Module extends SimpleModule {

    public OAuth2AuthorizationJackson2Module() {
        super(OAuth2AuthorizationJackson2Module.class.getName());
    }

    @Override
    public void setupModule(SetupContext context) {
        // 仅处理自定义类，标准的OAuth2类由官方模块处理
        context.setMixInAnnotations(AuthInfo.User.class, AuthInfoMixin.class);
        context.setMixInAnnotations(AuthInfo.Client.class, AuthInfoMixin.class);
    }

    /**
     * Mixin for {@link AuthInfo} related classes.
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    private abstract static class AuthInfoMixin {
    }

}
