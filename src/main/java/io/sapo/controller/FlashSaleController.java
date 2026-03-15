package io.sapo.controller;

import io.sapo.common.util.ResponseUtils;
import io.sapo.model.request.OrderRequest;
import io.sapo.model.response.common.BaseResponse;
import io.sapo.service.FlashSaleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/flash-sale")
@CrossOrigin(originPatterns = "*")
@AllArgsConstructor
public class FlashSaleController {
    private final FlashSaleService flashSaleService;

    @PostMapping("/order")
    public ResponseEntity<BaseResponse<Object>> order(@RequestBody OrderRequest request) {
        flashSaleService.initializeOrder(request);
        return ResponseEntity.ok(ResponseUtils.success("Initialized order, waiting for processing"));
    }

    @GetMapping("/products")
    public ResponseEntity<BaseResponse<Object>> getProducts() {
        var page = (Page) flashSaleService.listProduct();
        return ResponseEntity.ok(ResponseUtils.success(null, page.getContent(), page));
    }
}
