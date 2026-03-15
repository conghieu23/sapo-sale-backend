package io.sapo.service.implement;

import io.sapo.common.exception.CustomBusinessException;
import io.sapo.model.Order;
import io.sapo.model.Product;
import io.sapo.model.request.OrderRequest;
import io.sapo.repository.OrderRepository;
import io.sapo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "flash-sale-order")
    private void consume(OrderRequest request) {
        this.createOrder(request);
    }

    @Transactional
    protected void createOrder(OrderRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new CustomBusinessException("Not found product"));

        product.setStock(product.getStock() - request.getQuantity());
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .userId(request.getUserId())
                .product(product)
                .quantity(request.getQuantity())
                .build();
        orderRepository.save(order);
    }
}
