package com._37collaborationserver.service;

import com._37collaborationserver.domain.Product;
import com._37collaborationserver.dto.ProductResponse;
import com._37collaborationserver.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts(String sortBy) {
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
}