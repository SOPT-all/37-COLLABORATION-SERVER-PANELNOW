package com._37collaborationserver.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long price;

    private String name;

    private String imageUrl;

    //소요일
    private String day;

    //이용안내
    private String info;

    //사용방법
    private String usageManual;

    //이용가이드
    private String guide;

}
