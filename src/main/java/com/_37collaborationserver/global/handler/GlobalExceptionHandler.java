package com._37collaborationserver.global.handler;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com._37collaborationserver.global.exception.code.ErrorCode;
import com._37collaborationserver.global.exception.dto.ErrorResponse;

import jakarta.validation.ConstraintDeclarationException;
import jakarta.validation.ConstraintViolation;

@RestControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		return buildErrorResponse(ErrorCode.INVALID_FIELD_ERROR, ex.getBindingResult());
	}

	@ExceptionHandler(ConstraintDeclarationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolation ex) {
		return buildErrorResponse(ErrorCode.INVALID_FIELD_ERROR, ex.getMessage());
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
		MissingServletRequestParameterException ex) {
		return buildErrorResponse(ErrorCode.MISSING_PARAMETER, ex.getParameterName());
	}

	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
		return buildErrorResponse(ErrorCode.MISSING_HEADER, ex.getHeaderName());
	}


	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
		return buildErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage());

	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		String detail = ex.getRequiredType() != null
			? String.format("'%s'은(는) %s 타입이어야 합니다.", ex.getName(), ex.getRequiredType().getSimpleName())
			: "타입 변환 오류입니다.";
		return buildErrorResponse(ErrorCode.TYPE_MISMATCH, detail);
	}

	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(HandlerMethodValidationException ex) {
		return buildErrorResponse(ErrorCode.INVALID_FIELD_ERROR, ex.getMessage());
	}

	private ResponseEntity<ErrorResponse> buildErrorResponse(ErrorCode errorCode, Object detail) {
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(ErrorResponse.of(errorCode, detail));
	}
}
