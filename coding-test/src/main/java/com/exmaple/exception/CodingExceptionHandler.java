package com.exmaple.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CodingExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
	public ResponseEntity<ApiError> handleAuthenticationException(Exception ex) {
		ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.toString(),
				"Unauthorized User Credentials were Found");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

	@ExceptionHandler({ IOException.class })
    @ResponseBody
	public ResponseEntity<ApiError> handleIOException(Exception ex) {
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST.toString(),
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
