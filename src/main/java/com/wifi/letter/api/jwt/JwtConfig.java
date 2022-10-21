package com.wifi.letter.api.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "jwt.config")
@Data
public class JwtConfig {
    private String key;

    public Integer getUidFromHeader(Map<String, String> header) {
        String token = header.get("x-token");
        if (token.isBlank()) {
            return 0;
        }

        return JwtUtil.parseJwt(token, this.getKey());
    }
}
