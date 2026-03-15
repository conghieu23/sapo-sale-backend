package io.sapo.model.request;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderRequest {
    private UUID productId;
    private UUID userId;
    private int quantity;
}
