package com._37collaborationserver.controller;

import com._37collaborationserver.dto.ProductResponse;
import com._37collaborationserver.global.exception.code.SuccessCode;
import com._37collaborationserver.global.exception.dto.SuccessResponse;
import com._37collaborationserver.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}