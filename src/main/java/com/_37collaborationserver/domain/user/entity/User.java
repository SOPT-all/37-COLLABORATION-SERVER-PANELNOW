package com._37collaborationserver.domain.user.entity;

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
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String currentPoint;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private String usedPoint;

	public User(String currentPoint, String phoneNumber, String usedPoint) {
		this.currentPoint = currentPoint;
		this.phoneNumber = phoneNumber;
		this.usedPoint = usedPoint;
	}
}
