package io.sapo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    private UUID id;

    private UUID userId;
    @Column(check = @CheckConstraint(constraint = "quantity > 0"))
    private int quantity;
    @ManyToOne
    private Product product;
}
