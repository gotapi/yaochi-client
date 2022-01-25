package de.shifen.yaochi.client.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ms404 <yaochi.github@404.ms>
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
    @Value("${yaochi.basicAuth.name}")
    private String basicAuthName;
    @Value("${yaochi.basicAuth.pass}")
    private String basicAuthPass;
}
