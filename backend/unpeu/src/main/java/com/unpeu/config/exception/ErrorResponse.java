package com.unpeu.config.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Getter;

/**
 * User에게 보낼 응답 Format을 정의한 ErrorResponse Class 입니다
 */
@Getter
@Builder
public class ErrorResponse {
	private final LocalDateTime timestamp = LocalDateTime.now();
	private final int status;
	private final String error;
	private final String code;
	private final String message;

	public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
		return ResponseEntity
			.status(errorCode.getHttpStatus())
			.body(ErrorResponse.builder()
				.status(errorCode.getHttpStatus().value())
				.error(errorCode.getHttpStatus().name())
				.code(errorCode.name())
				.message(errorCode.getDetail())
				.build()
			);
	}
	public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode, String message) {
		return ResponseEntity
			.status(errorCode.getHttpStatus())
			.body(ErrorResponse.builder()
				.status(errorCode.getHttpStatus().value())
				.error(errorCode.getHttpStatus().name())
				.code(errorCode.name())
				.message(message)
				.build()
			);
	}

	public static ResponseEntity<Object> toObjResponseEntity(ErrorCode errorCode, List<String> errorList) {
		return ResponseEntity
			.status(errorCode.getHttpStatus())
			.body(ErrorResponse.builder()
				.status(errorCode.getHttpStatus().value())
				.error(errorCode.getHttpStatus().name())
				.code(errorCode.name())
				.message(errorList.toString())
				.build()
			);
	}
}
