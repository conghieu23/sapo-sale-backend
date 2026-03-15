package io.sapo;

import io.sapo.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@SpringBootApplication
@AllArgsConstructor
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void initializeFlashSale() {
        productRepository.findAll()
                .forEach(product -> {
                    redisTemplate.opsForValue().set(
                            "flash-sale:" + product.getId(),
                            product.getStock(),
                            // Simulate flash sale in 1 hour
                            Duration.ofHours(1)
                    );
                });

    }
}
