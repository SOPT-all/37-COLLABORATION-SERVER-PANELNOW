package com._37collaborationserver.domain.product.service;

import com._37collaborationserver.domain.product.dto.ProductItemResponse;
import com._37collaborationserver.domain.product.entity.Product;
import com._37collaborationserver.domain.product.repository.ProductRepository;
import com._37collaborationserver.domain.product.dto.ProductResponse;
import com._37collaborationserver.domain.user.entity.User;
import com._37collaborationserver.domain.user.repository.UserRepository;
import com._37collaborationserver.global.exception.BadRequestException;
import com._37collaborationserver.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com._37collaborationserver.global.exception.code.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private static final List<String> VALID_SORT_OPTIONS = List.of("default", "price_asc", "price_desc");
    private static final Long DEFAULT_USER_ID = 1L;

    public List<ProductResponse> getAllProducts(String sortBy) {

        if (sortBy != null && !VALID_SORT_OPTIONS.contains(sortBy.toLowerCase())) {
            throw new BadRequestException(ErrorCode.INVALID_SORT_OPTION);
        }

        List<Product> products;

        if ("price_asc".equalsIgnoreCase(sortBy)) {
            products = productRepository.findAllByOrderByPriceAsc();
        } else if ("price_desc".equalsIgnoreCase(sortBy)) {
            products = productRepository.findAllByOrderByPriceDesc();
        } else {
            // defalut
            products = productRepository.findAll();
        }

        return products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public ProductItemResponse getProductById(Long productId, Long userId) {
        // 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        return ProductItemResponse.from(product, user.getPhoneNumber());
    }
}