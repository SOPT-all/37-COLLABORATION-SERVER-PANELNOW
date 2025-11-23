package com._37collaborationserver.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

	@Column(nullable = true)
	private String info;

	@Column(nullable = true, name = "usage_manual")
	private String usageManual;

	@Column(nullable = true)
	private String guide;

	@Column(nullable = false)
	private String name;

	public Product(int price, String imageUrl, String day, String info, String manual, String guide, String name) {
		this.price = price;
		this.imageUrl = imageUrl;
		this.day = day;
		this.info = info;
		this.usageManual = usageManual;
		this.guide = guide;
		this.name = name;
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
}
