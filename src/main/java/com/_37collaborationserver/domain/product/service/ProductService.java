package com._37collaborationserver.domain.product.service;

import com._37collaborationserver.domain.product.dto.ProductItemResponse;
import com._37collaborationserver.domain.product.entity.Product;
import com._37collaborationserver.domain.product.entity.UserProduct;
import com._37collaborationserver.domain.product.repository.ProductRepository;
import com._37collaborationserver.domain.product.dto.ProductResponse;
import com._37collaborationserver.domain.product.repository.UserProductRepository;
import com._37collaborationserver.domain.user.entity.User;
import com._37collaborationserver.domain.user.repository.UserRepository;
import com._37collaborationserver.domain.user.service.UserService;
import com._37collaborationserver.global.exception.BadRequestException;
import com._37collaborationserver.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com._37collaborationserver.global.exception.code.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final UserProductRepository userProductRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ProductTransactionService productTransactionService;


    private static final List<String> VALID_SORT_OPTIONS = List.of("default", "price_asc", "price_desc");
    private static final Long DEFAULT_USER_ID = 1L;
    private static final int MAX_RETRY_COUNT = 3; // 재시도 최대 횟수

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

    public void changePoint(final Long userId, final Long productId) {
        int retryCount = 0;

        while (retryCount < MAX_RETRY_COUNT) {
            try {
                // 실제 교환 로직 실행
                productTransactionService.executeChangePoint(userId, productId);
                return; // 성공 시 메서드 종료

            } catch (ObjectOptimisticLockingFailureException e) {
                // 낙관적 락 충돌 발생
                retryCount++;

                log.warn("낙관적 락 충돌 발생 - 재시도 {}/{} (userId: {}, productId: {})",
                        retryCount, MAX_RETRY_COUNT, userId, productId);

                if (retryCount >= MAX_RETRY_COUNT) {
                    log.error("최대 재시도 횟수 초과 (userId: {}, productId: {})", userId, productId);
                    throw new BadRequestException(ErrorCode.EXCHANGE_FAILED_DUE_TO_CONCURRENCY);
                }

                // 재시도 전 짧은 대기
                try {
                    Thread.sleep(50 * retryCount);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new BadRequestException(ErrorCode.EXCHANGE_FAILED_DUE_TO_CONCURRENCY);
                }
            }
        }
    }

    private Product findProductById(final Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
    }
}