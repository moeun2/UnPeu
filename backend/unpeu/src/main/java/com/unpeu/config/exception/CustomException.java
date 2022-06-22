package com.unpeu.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * ErrorCode에 따라 사용할 수 있는 Custom Exception Class 입니다.
 */
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
	private final ErrorCode errorCode;
}
