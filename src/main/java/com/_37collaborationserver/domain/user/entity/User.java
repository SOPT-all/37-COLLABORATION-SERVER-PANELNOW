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

	@Column(nullable = false, name = "current_point")
	private int currentPoint;

	@Column(nullable = false, name = "phone_number")
	private String phoneNumber;

	@Column(nullable = false, name = "used_point")
	private int usedPoint;

	public User(int currentPoint, String phoneNumber, int usedPoint) {
		this.currentPoint = currentPoint;
		this.phoneNumber = phoneNumber;
		this.usedPoint = usedPoint;
	}

	public void updatePoint(int point){
		this.currentPoint -= point;
		this.usedPoint += point;
	}
}
