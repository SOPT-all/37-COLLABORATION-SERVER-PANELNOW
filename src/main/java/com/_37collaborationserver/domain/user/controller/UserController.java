package com._37collaborationserver.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._37collaborationserver.domain.user.dto.UserInfoResponse;
import com._37collaborationserver.domain.user.service.UserService;
import com._37collaborationserver.global.exception.code.SuccessCode;
import com._37collaborationserver.global.exception.dto.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
	private final UserService userService;

	@GetMapping
	public ResponseEntity<SuccessResponse<UserInfoResponse>> getUserInfo(
		@RequestHeader(defaultValue = "1") Long userId
	) {
		UserInfoResponse userInfoResponse = userService.getUserInfo(userId);
		return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_FETCH, userInfoResponse));
	}
}
