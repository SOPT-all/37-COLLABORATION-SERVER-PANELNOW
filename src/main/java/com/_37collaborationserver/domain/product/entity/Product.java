package com._37collaborationserver.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private String imageUrl;

	@Column(nullable = false)
	private String day;

	@Column(nullable = false)
	private String info;

	@Column(nullable = false)
	private String manual;

	@Column(nullable = false)
	private String guide;

	@Column(nullable = false)
	private String name;

	public Product(int price, String imageUrl, String day, String info, String manual, String guide, String name) {
		this.price = price;
		this.imageUrl = imageUrl;
		this.day = day;
		this.info = info;
		this.manual = manual;
		this.guide = guide;
		this.name = name;
	}
}
