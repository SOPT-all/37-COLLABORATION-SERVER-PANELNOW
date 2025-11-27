package com._37collaborationserver.domain.product.service;

import com._37collaborationserver.domain.product.entity.Product;
import com._37collaborationserver.domain.product.entity.UserProduct;
import com._37collaborationserver.domain.product.repository.ProductRepository;
import com._37collaborationserver.domain.product.repository.UserProductRepository;
import com._37collaborationserver.domain.user.entity.User;
import com._37collaborationserver.domain.user.repository.UserRepository;
import com._37collaborationserver.domain.user.service.UserService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductTransactionService {

    private final ProductRepository productRepository;
    private final UserProductRepository userProductRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    /**
     * 실제 포인트 교환 로직 (트랜잭션 내부)
     *
     * 이 메서드는 별도 클래스에 있어야 @Transactional이 제대로 작동함!
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void executeChangePoint(final Long userId, final Long productId) {
        // 1. 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다"));

        // 2. 상품 조회 (version 포함)
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다"));

        log.info("조회 완료 - userId: {}, productId: {}, 재고: {}, version: {}",
                userId, productId, product.getStock(), product.getVersion());

        // 3. 재고 확인
        if (!product.hasStock(1)) {
            log.warn("재고 부족 (productId: {}, 현재 재고: {})", productId, product.getStock());
            throw new IllegalStateException("재고 부족");
        }

        // 4. 포인트 확인
        if (user.getCurrentPoint() < product.getPrice()) {
            throw new IllegalStateException("포인트 부족");
        }

        // 5. 재고 감소 (Dirty Checking으로 자동 UPDATE)
        product.decreaseStock(1);

        // 6. 포인트 차감 (Dirty Checking으로 자동 UPDATE)
        user.updatePoint(product.getPrice());

        // 7. 교환 내역 저장
        UserProduct userProduct = new UserProduct(user, product);
        userProductRepository.save(userProduct);

        entityManager.flush();

        log.info("교환 성공 (userId: {}, productId: {}, version: {})",
                userId, productId, product.getVersion());
    }

    private Product findProductById(final Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다"));
    }
}