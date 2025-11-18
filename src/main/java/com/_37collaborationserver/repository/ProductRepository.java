package com._37collaborationserver.repository;

import com._37collaborationserver.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 가격 오름차순 정렬
    List<Product> findAllByOrderByPriceAsc();

    // 가격 내림차순 정렬
    List<Product> findAllByOrderByPriceDesc();
}
