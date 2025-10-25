package com.gexingw.mall.auth.service.infra.support.auth;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * TokenSettings 的自定义反序列化器
 *
 * @author GeXingW
 */
public class TokenSettingsDeserializer extends JsonDeserializer<TokenSettings> {

    @Override
    public TokenSettings deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        ObjectNode root = parser.getCodec().readTree(parser);
        
        // 首先检查是否存在 settings 对象
        ObjectNode settingsNode = null;
        if (root.has("settings") && root.get("settings").isObject()) {
            settingsNode = (ObjectNode) root.get("settings");
        }
        
        // 获取必要的字段，优先从根对象获取，如果不存在则尝试从 settings 对象获取
        Duration accessTokenTimeToLive = parseDuration(root, "accessTokenTimeToLive");
        if (accessTokenTimeToLive == null && settingsNode != null) {
            accessTokenTimeToLive = parseDuration(settingsNode, "settings.token.access-token-time-to-live");
        }
        
        OAuth2TokenFormat accessTokenFormat = parseTokenFormat(root, "accessTokenFormat");
        if (accessTokenFormat == null && settingsNode != null) {
            accessTokenFormat = parseTokenFormat(settingsNode, "settings.token.access-token-format");
        }
        
        Duration refreshTokenTimeToLive = parseDuration(root, "refreshTokenTimeToLive");
        if (refreshTokenTimeToLive == null && settingsNode != null) {
            refreshTokenTimeToLive = parseDuration(settingsNode, "settings.token.refresh-token-time-to-live");
        }
        
        boolean reuseRefreshTokens = root.has("reuseRefreshTokens") && root.get("reuseRefreshTokens").asBoolean();
        if (!reuseRefreshTokens && settingsNode != null) {
            reuseRefreshTokens = settingsNode.has("settings.token.reuse-refresh-tokens") && 
                                settingsNode.get("settings.token.reuse-refresh-tokens").asBoolean();
        }
        
        SignatureAlgorithm idTokenSignatureAlgorithm = parseSignatureAlgorithm(root, "idTokenSignatureAlgorithm");
        if (idTokenSignatureAlgorithm == null && settingsNode != null) {
            idTokenSignatureAlgorithm = parseSignatureAlgorithm(settingsNode, "settings.token.id-token-signature-algorithm");
        }
        
        // 构建 TokenSettings
        TokenSettings.Builder builder = TokenSettings.builder();
        
        if (accessTokenTimeToLive != null) {
            builder.accessTokenTimeToLive(accessTokenTimeToLive);
        }
        
        if (accessTokenFormat != null) {
            builder.accessTokenFormat(accessTokenFormat);
        }
        
        if (refreshTokenTimeToLive != null) {
            builder.refreshTokenTimeToLive(refreshTokenTimeToLive);
        }
        
        builder.reuseRefreshTokens(reuseRefreshTokens);
        
        if (idTokenSignatureAlgorithm != null) {
            builder.idTokenSignatureAlgorithm(idTokenSignatureAlgorithm);
        }
        
        return builder.build();
    }
    
    private Duration parseDuration(ObjectNode node, String fieldName) {
        if (!node.has(fieldName)) {
            return null;
        }
        
        JsonNode durationNode = node.get(fieldName);
        if (durationNode.isTextual()) {
            return Duration.parse(durationNode.asText());
        } else if (durationNode.isObject() && durationNode.has("seconds")) {
            return Duration.ofSeconds(durationNode.get("seconds").asLong());
        } else if (durationNode.isArray() && durationNode.size() == 2) {
            // 处理 ["java.time.Duration", 300.0] 格式
            JsonNode valueNode = durationNode.get(1);
            if (valueNode.isNumber()) {
                return Duration.ofSeconds(valueNode.asLong());
            }
        }
        
        return null;
    }
    
    private OAuth2TokenFormat parseTokenFormat(ObjectNode node, String fieldName) {
        if (!node.has(fieldName)) {
            return null;
        }
        
        JsonNode formatNode = node.get(fieldName);
        if (formatNode.isTextual()) {
            String value = formatNode.asText();
            if ("self-contained".equals(value)) {
                return OAuth2TokenFormat.SELF_CONTAINED;
            } else if ("reference".equals(value)) {
                return OAuth2TokenFormat.REFERENCE;
            }
        } else if (formatNode.isObject()) {
            if (formatNode.has("value")) {
                String value = formatNode.get("value").asText();
                if ("self-contained".equals(value)) {
                    return OAuth2TokenFormat.SELF_CONTAINED;
                } else if ("reference".equals(value)) {
                    return OAuth2TokenFormat.REFERENCE;
                }
            }
        }
        
        return null;
    }
    
    private SignatureAlgorithm parseSignatureAlgorithm(ObjectNode node, String fieldName) {
        if (!node.has(fieldName)) {
            return null;
        }
        
        JsonNode algorithmNode = node.get(fieldName);
        if (algorithmNode.isTextual()) {
            return SignatureAlgorithm.from(algorithmNode.asText());
        } else if (algorithmNode.isObject() && algorithmNode.has("name")) {
            return SignatureAlgorithm.from(algorithmNode.get("name").asText());
        } else if (algorithmNode.isArray() && algorithmNode.size() == 2) {
            // 处理 ["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm", "RS256"] 格式
            JsonNode valueNode = algorithmNode.get(1);
            if (valueNode.isTextual()) {
                return SignatureAlgorithm.from(valueNode.asText());
            }
        }
        
        return null;
    }
}
