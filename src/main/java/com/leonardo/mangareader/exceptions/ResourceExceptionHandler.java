package com.leonardo.mangareader.exceptions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
		Map<String, String> errors = new HashMap<>();

		e.getBindingResult().getAllErrors().forEach(err -> {
			String field = ((FieldError) err).getField();
			String value = err.getDefaultMessage();
			errors.put(field, value);
		});	

		StandardError err = new StandardError(
			System.currentTimeMillis(),
			HttpStatus.UNPROCESSABLE_ENTITY.value(),
			errors,
			e.getMessage(),
			request.getRequestURI()
		);

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}


	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException e, HttpServletRequest request){

		StandardError err = new StandardError(
			System.currentTimeMillis(),
			HttpStatus.NOT_FOUND.value(),
			"O objeto não pode ser encontrado.",
			e.getMessage(),
			request.getRequestURI()
		);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrityException(DataIntegrityException e, HttpServletRequest request){

		StandardError err = new StandardError(
			System.currentTimeMillis(),
			HttpStatus.BAD_REQUEST.value(),
			"Esse objeto não pode ser persistido por motivos de integridade da base de dados.",
			e.getMessage(),
			request.getRequestURI()
		);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}
