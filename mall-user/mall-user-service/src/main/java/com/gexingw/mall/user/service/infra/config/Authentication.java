package com.gexingw.mall.user.service.infra.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.gexingw.mall.infrastucture.core.domain.AuthInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * mall-cloud
 *
 * @author GeXingW
 * @date 2024/8/26 18:24
 */
@NoArgsConstructor
@JsonTypeInfo(use = Id.CLASS, property = "@class")
public class Authentication implements org.springframework.security.core.Authentication {

    private boolean authenticated;

    @Getter
    private AuthInfo authInfo;

    public Authentication(@NotNull AuthInfo authInfo) {
        this.authInfo = authInfo;
        this.authenticated = true;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public Object getCredentials() {
        return null;
    }

    @Override
    @JsonIgnore
    public Object getDetails() {
        return null;
    }

    @Override
    @JsonIgnore
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    @JsonIgnore
    public String getName() {
        return this.getAuthInfo().getAuthUserName();
    }

}
