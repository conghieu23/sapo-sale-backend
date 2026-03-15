package io.sapo.config.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("spring.data.redis")
public class RedisProps {
    private String host;
    private int port;
    private String password;
    private long timeout;
}
