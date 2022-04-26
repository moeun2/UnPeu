package com.unpeu.config.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 모든 예외 케이스를 이곳에서 관리합니다.
 * ResponseStatusException과 비슷하지만 개발자가 정의한 새로운 Exception을 한 곳에서 관리할 수 있음
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

	/* 400 BAD_REQUEST : 잘못된 요청 */
	INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
	MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),

	/* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
	INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
	UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),

	/* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
	MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
	PRESENT_NOT_FOUND(NOT_FOUND, "해당 선물 정보를 찾을 수 없습니다"),
	MESSAGE_NOT_FOUND(NOT_FOUND, "해당 메세지 정보를 찾을 수 없습니다"),
	MESSAGE_NOT_FOUND_BY_USER(NOT_FOUND, "해당 USER에 따른 메세지 정보를 찾을 수 없습니다"),
	REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),


	/* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
	DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
	DELETE_CONFLICT(CONFLICT, "해당 삭제에 충돌이 존재합니다"),

	;

	private final HttpStatus httpStatus;
	private final String detail;
}
