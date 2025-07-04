package com.gexingw.mall.auth.service.infra.dao.regsteredclient.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gexingw.mall.infrastructure.db.BasePO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * mall-user-service
 *
 * @author GeXingW
 * @date 2024/2/17 13:03
 */
@Data
@TableName("oauth2_registered_client")
public class RegisteredClientPO extends BasePO {

    private String clientName;

    private String clientId;

    private LocalDateTime clientIdIssuedAt;

    private String clientSecret;

    private LocalDateTime clientSecretExpiresAt;

    private String clientAuthenticationMethods;

    private String authorizationGrantTypes;

    private String redirectUris;

    private String scopes;

    private String clientSettings;

    private String tokenSettings;

    /**
     * 客户端类型
     */
    private Integer clientType;

}
