package com._37collaborationserver.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Version;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicUpdate
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private String imageUrl;

	@Column(nullable = false,name = "delivery_day")
	private String day;

	@Column(nullable = true)
	private String info;

	@Column(nullable = true, name = "usage_manual")
	private String usageManual;

	@Column(nullable = true)
	private String guide;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private int stock;

	@Version
	private Long version;

	public Product(int price, String imageUrl, String day, String info, String manual, String guide, String name,int stock) {
		this.price = price;
		this.imageUrl = imageUrl;
		this.day = day;
		this.info = info;
		this.usageManual = manual;
		this.guide = guide;
		this.name = name;
		this.stock = stock;
	}

	public String getExchangeDate() {
		try {
			int daysToAdd = Integer.parseInt(this.day.replaceAll("[^0-9]", ""));
			LocalDate exchangeDate = LocalDate.now().plusDays(daysToAdd);
			return exchangeDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
		} catch (NumberFormatException e) {
			return this.day;
		}
	}

	public void decreaseStock(int quantity) {
		if (this.stock < quantity) {
			throw new IllegalStateException("재고가 부족합니다. 현재 재고: " + this.stock);
		}
		this.stock -= quantity;
	}

	public boolean hasStock(int quantity) {
		return this.stock >= quantity;
	}
}
