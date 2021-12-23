package de.shifen.yaochi.client.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xurenlu
 */
@Data
@Component
public class YaochiConfig {
    @Value("${yaochi.appName}")
    private String businessAppName;
    @Value("${yaochi.serverAddress}")
    private String serverAddress;
    @Value("${yaochi.autoLogAttributes}")
    private String autoLogAttributes;
}
