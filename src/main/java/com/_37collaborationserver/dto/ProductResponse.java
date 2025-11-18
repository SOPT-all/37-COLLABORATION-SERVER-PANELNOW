package com._37collaborationserver.dto;

import com._37collaborationserver.domain.Product;
import lombok.Builder;

@Builder
public record ProductResponse(
        Long id,
        String imageUrl,
        String name,
        Long price,
        String day
) {
    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .imageUrl(product.getImageUrl())
                .name(product.getName())
                .price(product.getPrice())
                .day(product.getDay())
                .build();
    }
}
