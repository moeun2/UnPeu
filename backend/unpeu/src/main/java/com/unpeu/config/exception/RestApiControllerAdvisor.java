package com.unpeu.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



/**
 * 모든 Controller에 대한 전역적으로 발생할 수 있는 에외를 잡아서 처리한다.
 */
@Slf4j
@RestControllerAdvice
public class RestApiControllerAdvisor {

	/**
	 * @valid로 유효성 검사했을 때 발생하는 에러 처리
	 */
	// @ExceptionHandler(MethodArgumentNotValidException.class)
	// public ResponseEntity<Object> methodValidationException(MethodArgumentNotValidException e) {
	// 	log.warn("MethodArgumentNotValidException 발생");
	// 	BindingResult bindingResult = e.getBindingResult();
	//
	// 	StringBuilder builder = new StringBuilder();
	// 	for (FieldError fieldError : bindingResult.getFieldErrors()) {
	// 		builder.append(fieldError.getField()).append(" : ");
	// 		builder.append(fieldError.getDefaultMessage());
	// 		builder.append(", ");
	// 	}
	// 	return new ResponseEntity<>(builder.toString(), HttpStatus.BAD_REQUEST);
	// }

}
