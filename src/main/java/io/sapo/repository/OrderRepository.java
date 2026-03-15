package io.sapo.repository;

import io.sapo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("""
        select coalesce(sum(quantity), 0)
        from Order
        where userId = :userId and product.id = :productId
        """)
    int getOrderedQuantity(UUID userId, UUID productId);
}
