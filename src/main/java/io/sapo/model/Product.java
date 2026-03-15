package io.sapo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    private UUID id;

    private String name;
    @NotNull
    private float price;
    @Builder.Default
    @Column(check = @CheckConstraint(constraint = "stock >= 0"))
    private int stock = 0;
}
