package com._37collaborationserver.domain.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._37collaborationserver.domain.product.service.ProductService;
import com._37collaborationserver.global.exception.code.SuccessCode;
import com._37collaborationserver.global.exception.dto.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
	private final ProductService productService;

	@PostMapping("/{productId}/purchase")
	public ResponseEntity<SuccessResponse<?>> purchase(
		@RequestHeader(defaultValue = "1") Long userId,
		@PathVariable(name = "productId") Long productId
	){
		productService.changePoint(userId, productId);
		return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CREATE));
	}
}
