package com._37collaborationserver.domain.product.dto;

import com._37collaborationserver.domain.product.entity.Product;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;


@Builder
public record ProductItemResponse(
        Long id,
        String imageUrl,
        String name,
        int price,
        String phoneNumber,
        String exchangeDay,
        /*
        String info,
        String usageManual,
        String guide

         */
        List<InfoSection> infoSections

) {
    @Builder
    public record InfoSection(
            String label,
            String content
    ) {}

    public static ProductItemResponse from(Product product,String phoneNumber) {
        List<InfoSection> sections = buildInfoSections(product);

        return ProductItemResponse.builder()
                .id(product.getId())
                .imageUrl(product.getImageUrl())
                .name(product.getName())
                .price(product.getPrice())
                .phoneNumber(phoneNumber)
                .exchangeDay(product.getExchangeDate())
                //.info(product.getInfo())
                //.usageManual(product.getUsageManual())
                //.guide(product.getGuide())
                .infoSections(sections)
                .build();
    }
    private static List<InfoSection> buildInfoSections(Product product) {
        Long productId = product.getId();
        List<InfoSection> sections = new ArrayList<>();

        // 상품 ID에 따라 다른 라벨 적용
        if (productId == 3L || productId == 6L) {
            // 네이버페이 충전권 타입
            if (product.getInfo() != null) {
                sections.add(new InfoSection("충전권 이용안내", product.getInfo()));
            }
            if (product.getUsageManual() != null) {
                sections.add(new InfoSection("사용방법", product.getUsageManual()));
            }
            if (product.getGuide() != null) {
                sections.add(new InfoSection("이용가이드", product.getGuide()));
            }
        } else if (productId == 10L) {
            // GS25 상품권 타입
            if (product.getInfo() != null) {
                sections.add(new InfoSection("상품권 이용안내", product.getInfo()));
            }
            if (product.getUsageManual() != null) {
                sections.add(new InfoSection("사용불가매장", product.getUsageManual()));
            }
            if (product.getGuide() != null) {
                sections.add(new InfoSection("상품교환방법(점포용)", product.getGuide()));
            }
        } else {
            // 기본 타입
            if (product.getInfo() != null) {
                sections.add(new InfoSection("이용 안내", product.getInfo()));
            }
            if (product.getUsageManual() != null) {
                sections.add(new InfoSection("사용 방법", product.getUsageManual()));
            }
            if (product.getGuide() != null) {
                sections.add(new InfoSection("이용 가이드", product.getGuide()));
            }
        }
        return sections;
    }
}
