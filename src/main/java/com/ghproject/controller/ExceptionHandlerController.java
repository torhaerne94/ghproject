package com.ghproject.controller;

import com.ghproject.exception.ResourceNotFoundException;
import com.ghproject.exception.SimpleExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
	@ExceptionHandler({ResourceNotFoundException.class})
	public ResponseEntity<SimpleExceptionDTO> resourceNotFoundExceptionHandler(ResourceNotFoundException rnfe) {
		return new ResponseEntity<>(new SimpleExceptionDTO(rnfe.getResponseCode(), rnfe.getMessage()), HttpStatus.NOT_FOUND);
	}
}

