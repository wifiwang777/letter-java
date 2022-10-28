package com.wifi.letter.api.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "custom.config")
@Data
@Component
public class CustomConfig {
    /**
     * 不需要拦截的地址
     */
    private IgnoreConfig ignores;
}
