package com._37collaborationserver.global.exception.code;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SuccessCode {
	/* 201 CREATED */
	SUCCESS_CREATE(HttpStatus.CREATED, "생성이 완료되었습니다"),

	/* 200 OK */
	SUCCESS_FETCH(HttpStatus.OK, "조회가 완료되었습니다"),
	SUCCESS_DELETE(HttpStatus.OK, "삭제가 완료되었습니다"),
	SUCCESS_UPDATE(HttpStatus.OK, "업데이트가 완료되었습니다");

	private final HttpStatus httpStatus;
	private final String message;

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}
}
