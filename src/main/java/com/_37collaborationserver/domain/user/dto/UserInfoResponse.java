package com._37collaborationserver.domain.user.dto;

import com._37collaborationserver.domain.user.entity.User;

public record UserInfoResponse(
	String currentPoint,
	String usedPoint
) {
	public static UserInfoResponse from(User user) {
		return new UserInfoResponse(
			user.getCurrentPoint(),
			user.getUsedPoint()
		);
	}
}
