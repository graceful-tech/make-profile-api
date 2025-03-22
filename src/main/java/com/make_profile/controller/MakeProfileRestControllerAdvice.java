package com.make_profile.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.make_profile.exception.MakeProfileException;

@RestControllerAdvice
public class MakeProfileRestControllerAdvice extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(MakeProfileRestControllerAdvice.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
		return new ResponseEntity<>(buildResponse(ex), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { MakeProfileException.class })
	public ResponseEntity<?> handleException(MakeProfileException exception) {
		logger.error("Controller :: handleException :: MakeProfileException :: " + exception.getCode());
		return new ResponseEntity<>(buildResponse(exception.getCode()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
