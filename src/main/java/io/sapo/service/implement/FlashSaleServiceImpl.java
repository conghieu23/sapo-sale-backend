package io.sapo.service.implement;

import io.sapo.common.exception.CustomBusinessException;
import io.sapo.model.request.OrderRequest;
import io.sapo.repository.ProductRepository;
import io.sapo.service.FlashSaleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class FlashSaleServiceImpl implements FlashSaleService {
    private final RedisTemplate<String, String> redisTemplate;
    private final KafkaTemplate<String, OrderRequest> kafkaTemplate;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public void initializeOrder(OrderRequest request) {
        String boughtKey = "bought:" + request.getProductId() +
                ":" + request.getUserId();

        int bought = 0;
        if (redisTemplate.hasKey(boughtKey)) {
            bought = Integer.valueOf(redisTemplate.opsForValue()
                    .get(boughtKey));
        } else {
            redisTemplate.opsForValue()
                    .setIfAbsent(boughtKey, "0");
        }
        
        // Examine bought limitation;
        if (bought + request.getQuantity() > 2) {
            throw new CustomBusinessException("Out of limitation");
        }

        String stockKey = "flash-sale:" + request.getProductId();
        // Examine product's stock;
        int remain = Integer.valueOf(redisTemplate.opsForValue()
                .get(stockKey));
        if (remain < 1) {
            throw new CustomBusinessException("Out of stock");
        } else if (request.getQuantity() > remain) {
            throw new CustomBusinessException("Over stock");
        }

        // Decrease product's stock in redis;
        redisTemplate.opsForValue()
                .decrement(stockKey, request.getQuantity());
        // Increase bought;
        redisTemplate.opsForValue()
                .increment(boughtKey, request.getQuantity());

        // Queue order;
        produce(request);
    }

    @Override
    public Object listProduct() {
        return productRepository.findAll(Pageable.ofSize(5));
    }

    private void produce(OrderRequest request) {
        kafkaTemplate.send("flash-sale-order", request);
    }
}
