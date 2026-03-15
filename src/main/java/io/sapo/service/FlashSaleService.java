package io.sapo.service;

import io.sapo.model.request.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public interface FlashSaleService {
    void initializeOrder(OrderRequest request);
    Object listProduct();
}
