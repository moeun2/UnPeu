package com.unpeu.config.exception;

/**
 * Application내에서 특이한 Exception이 아닐경우 Global로 사용할 수 있는 Exception
 */
public class ApplicationException extends RuntimeException {
	private static final Long serialVersionUID = 1L;

	public ApplicationException(String msg){
		super(msg);
	}
}
