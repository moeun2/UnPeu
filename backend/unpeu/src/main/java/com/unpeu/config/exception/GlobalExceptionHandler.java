package com.unpeu.config.exception;

import static com.unpeu.config.exception.ErrorCode.*;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Rest API Controller에서 발생되는 에러를 처리합니다
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * hibernate 관련 에러를 처리합니다
	 * @return
	 */
	@ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class})
	protected ResponseEntity<ErrorResponse> handleDataException() {
		log.error("handleDataException throw Exception : {}", DUPLICATE_RESOURCE);
		return ErrorResponse.toResponseEntity(DUPLICATE_RESOURCE);
	}

	/**
	 * CustomException을 사용하여 에러를 처리합니다.
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = { CustomException.class })
	protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
		log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
		return ErrorResponse.toResponseEntity(e.getErrorCode());
	}


}
