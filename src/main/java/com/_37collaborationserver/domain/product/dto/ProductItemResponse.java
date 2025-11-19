package com._37collaborationserver.domain.product.dto;

import com._37collaborationserver.domain.product.entity.Product;
import lombok.Builder;


@Builder
public record ProductItemResponse(
        Long id,
        String imageUrl,
        String name,
        int price,
        String phoneNumber,
        String exchangeDay,
        String info,
        String usageManual,
        String guide

) {
    public static ProductItemResponse from(Product product,String phoneNumber) {
        return ProductItemResponse.builder()
                .id(product.getId())
                .imageUrl(product.getImageUrl())
                .name(product.getName())
                .price(product.getPrice())
                .phoneNumber(phoneNumber)
                .exchangeDay(product.getExchangeDate())
                .info(product.getInfo())
                .usageManual(product.getUsageManual())
                .guide(product.getGuide())
                .build();
    }
}
