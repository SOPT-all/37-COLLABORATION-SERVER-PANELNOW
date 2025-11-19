package com._37collaborationserver.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com._37collaborationserver.domain.product.entity.UserProduct;

public interface UserProductRepository extends JpaRepository<UserProduct, Long> {
}
