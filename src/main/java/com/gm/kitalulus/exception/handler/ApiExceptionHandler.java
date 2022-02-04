/**
 * 
 */
package com.gm.kitalulus.exception.handler;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ghulam Mustafa
 *
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<?> exceptionHandler(Exception ex) {
	        log.error("Exception!",ex);
			if(ex instanceof ConstraintViolationException) {
				ConstraintViolationException constraintViolationException = (ConstraintViolationException)ex;
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ApiError.builder()
								.message(constraintViolationException.getMessage())
								.status(HttpStatus.BAD_REQUEST.value())
								.build());
			}
			//TODO: handle other exception types here
			return ResponseEntity.internalServerError().build();
	 }

}
