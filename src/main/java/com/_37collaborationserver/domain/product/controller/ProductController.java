package com._37collaborationserver.domain.product.controller;


import com._37collaborationserver.domain.product.dto.ProductItemResponse;
import com._37collaborationserver.domain.product.dto.ProductResponse;
import com._37collaborationserver.domain.product.service.ProductService;
import com._37collaborationserver.global.exception.code.SuccessCode;
import com._37collaborationserver.global.exception.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final DefaultErrorAttributes defaultErrorAttributes;

    @GetMapping
    public ResponseEntity<SuccessResponse<List<ProductResponse>>> getAllProducts(
            @RequestParam(required = false, defaultValue = "default") String sort
    ) {
        List<ProductResponse> products = productService.getAllProducts(sort);
        return ResponseEntity.ok(
                SuccessResponse.of(SuccessCode.SUCCESS_FETCH, products)
        );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<SuccessResponse<ProductItemResponse>> getProductById(
            @PathVariable Long productId,
            @RequestHeader(value = "userId", defaultValue = "1") String userId
    ) {
        ProductItemResponse product = productService.getProductById(productId, Long.valueOf(userId));
        return ResponseEntity.ok(
                SuccessResponse.of(SuccessCode.SUCCESS_FETCH, product)
        );
    }


}