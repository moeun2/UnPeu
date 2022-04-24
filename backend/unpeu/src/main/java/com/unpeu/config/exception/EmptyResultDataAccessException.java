package com.unpeu.config.exception;

/**
 * JPA Data
 */
public class EmptyResultDataAccessException extends RuntimeException {
    private static final Long serialVersionUID = 1L;

    public EmptyResultDataAccessException(String msg){
        super(msg);
    }
}
