package com.gexingw.mall.auth.service.infra.support.auth;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gexingw.mall.infrastucture.core.domain.AuthInfo;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

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

        // 添加 TokenSettings 相关类的支持
        context.setMixInAnnotations(TokenSettings.class, TokenSettingsMixin.class);
        context.setMixInAnnotations(ClientSettings.class, ClientSettingsMixin.class);
        context.setMixInAnnotations(OAuth2TokenFormat.class, OAuth2TokenFormatMixin.class);
    }

    /**
     * Mixin for {@link AuthInfo} related classes.
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    private abstract static class AuthInfoMixin {
    }

    /**
     * Mixin for {@link TokenSettings} class.
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    @JsonDeserialize(using = TokenSettingsDeserializer.class)
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
            isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public abstract static class TokenSettingsMixin {
    }

    /**
     * Mixin for {@link ClientSettings} class.
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    @JsonDeserialize(using = ClientSettingsDeserializer.class)
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
            isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public abstract static class ClientSettingsMixin {
    }

    /**
     * Mixin for {@link OAuth2TokenFormat} class.
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    @JsonDeserialize(using = OAuth2TokenFormatDeserializer.class)
    public abstract static class OAuth2TokenFormatMixin {
    }
}
