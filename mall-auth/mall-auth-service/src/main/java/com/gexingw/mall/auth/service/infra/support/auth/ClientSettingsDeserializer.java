package com.gexingw.mall.auth.service.infra.support.auth;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * ClientSettings 的自定义反序列化器
 *
 * @author GeXingW
 */
public class ClientSettingsDeserializer extends JsonDeserializer<ClientSettings> {

    @Override
    public ClientSettings deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        ObjectNode root = parser.getCodec().readTree(parser);

        // 获取必要的字段
        boolean requireProofKey = root.has("requireProofKey") && root.get("requireProofKey").asBoolean();
        boolean requireAuthorizationConsent = root.has("requireAuthorizationConsent") && root.get("requireAuthorizationConsent").asBoolean();
        String jwkSetUrl = root.has("jwkSetUrl") ? root.get("jwkSetUrl").asText() : null;
        Set<String> tokenEndpointAuthenticationSigningAlgorithms = parseStringSet(root, "tokenEndpointAuthenticationSigningAlgorithms");

//        c

        // 构建 ClientSettings
        ClientSettings.Builder builder = ClientSettings.builder()
                .requireProofKey(requireProofKey)
                .requireAuthorizationConsent(requireAuthorizationConsent);

        if (jwkSetUrl != null) {
            builder.jwkSetUrl(jwkSetUrl);
        }

//        if (tokenEndpointAuthenticationSigningAlgorithms != null && !tokenEndpointAuthenticationSigningAlgorithms.isEmpty()) {
//            builder.tokenEndpointAuthenticationSigningAlgorithm(tokenEndpointAuthenticationSigningAlgorithms);
//        }

        return builder.build();
    }

    private Set<String> parseStringSet(ObjectNode node, String fieldName) {
        if (!node.has(fieldName)) {
            return null;
        }

        JsonNode arrayNode = node.get(fieldName);
        if (!arrayNode.isArray()) {
            return null;
        }

        Set<String> result = new HashSet<>();
        Iterator<JsonNode> elements = arrayNode.elements();
        while (elements.hasNext()) {
            JsonNode element = elements.next();
            if (element.isTextual()) {
                result.add(element.asText());
            }
        }

        return result;
    }
}
