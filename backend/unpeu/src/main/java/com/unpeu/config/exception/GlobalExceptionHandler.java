package com.unpeu.config.exception;

import static com.unpeu.config.exception.ErrorCode.*;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

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


	/**
	 * @valid로 유효성 검사했을 때 발생하는 에러 처리
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> methodValidationException(MethodArgumentNotValidException e) {
		log.warn("MethodArgumentNotValidException 발생");
		BindingResult bindingResult = e.getBindingResult();

		StringBuilder builder = new StringBuilder();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			builder.append(fieldError.getField()).append(" : ");
			builder.append(fieldError.getDefaultMessage());
			builder.append(", ");
		}
		return new ResponseEntity<>(builder.toString(), HttpStatus.BAD_REQUEST);
	}
}
