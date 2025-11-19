package com._37collaborationserver.domain.product.controller;


import com._37collaborationserver.domain.product.dto.ProductResponse;
import com._37collaborationserver.domain.product.service.ProductService;
import com._37collaborationserver.global.exception.code.SuccessCode;
import com._37collaborationserver.global.exception.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<SuccessResponse<List<ProductResponse>>> getAllProducts(
            @RequestParam(required = false, defaultValue = "default") String sort
    ) {
        List<ProductResponse> products = productService.getAllProducts(sort);
        return ResponseEntity.ok(
                SuccessResponse.of(SuccessCode.SUCCESS_FETCH, products)
        );
    }
/*
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<ProductResponse>> getProductById(@PathVariable Long id) {}

 */
}