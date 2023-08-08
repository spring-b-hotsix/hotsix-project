package com.sparta.hotsixproject.exception;

import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// 에러 처리
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiResponseDto> handleNotFoundException(NotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ApiResponseDto> handleUnauthorizedException(UnauthorizedException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ApiResponseDto(ex.getMessage(), HttpStatus.UNAUTHORIZED.value()));
	}
}