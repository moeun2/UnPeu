package com.unpeu.config.exception;

import static com.unpeu.config.exception.ErrorCode.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
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
	 */
	@ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
	protected ResponseEntity<ErrorResponse> handleDataException(@NotNull Exception e) {
		log.error("handleDataException throw Exception : " + e);
		return ErrorResponse.toResponseEntity(DUPLICATE_RESOURCE, e.getMessage());
	}

	/**
	 * CustomException을 사용하여 에러를 처리합니다.
	 * @param e
	 */
	@ExceptionHandler(value = {CustomException.class})
	protected ResponseEntity<ErrorResponse> handleCustomException(@NotNull CustomException e) {
		log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
		return ErrorResponse.toResponseEntity(e.getErrorCode());
	}

	/**
	 * valid 유효성 검사했을 때 발생하는 에러 처리
	 */
	@Override
	protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(@NotNull MethodArgumentNotValidException ex,
		@NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
		log.error("MethodArgumentNotValidException : " + ex);
		List<String> errorList = ex
			.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.collect(Collectors.toList());
		return ErrorResponse.toObjResponseEntity(VALID_BAD_REQUEST, errorList);
	}

}
