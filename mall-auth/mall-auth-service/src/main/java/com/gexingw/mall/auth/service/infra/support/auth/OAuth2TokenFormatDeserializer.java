package com.gexingw.mall.auth.service.infra.support.auth;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import java.io.IOException;

/**
 * OAuth2TokenFormat 的自定义反序列化器
 *
 * @author GeXingW
 */
public class OAuth2TokenFormatDeserializer extends JsonDeserializer<OAuth2TokenFormat> {

    @Override
    public OAuth2TokenFormat deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        
        String value;
        if (node.isTextual()) {
            value = node.asText();
        } else if (node.isObject() && node.has("value")) {
            value = node.get("value").asText();
        } else {
            return null;
        }
        
        if ("self-contained".equals(value)) {
            return OAuth2TokenFormat.SELF_CONTAINED;
        } else if ("reference".equals(value)) {
            return OAuth2TokenFormat.REFERENCE;
        }
        
        return null;
    }
}
