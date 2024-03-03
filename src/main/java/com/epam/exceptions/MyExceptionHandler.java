package com.epam.exceptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.epam.entity.ErrorResponse;

@RestControllerAdvice
public class MyExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
		List<String> error = new ArrayList<>();
		e.getAllErrors().forEach(err -> error.add(err.getDefaultMessage()));
		return new ErrorResponse(new Date().toString(), HttpStatus.BAD_REQUEST.toString(), error.toString(),
				request.getDescription(false));

	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMethodArgumentMismatchException(MethodArgumentTypeMismatchException e,
			WebRequest request) {
		return new ErrorResponse(new Date().toString(), HttpStatus.BAD_REQUEST.toString(), e.getMessage(),
				request.getDescription(false));

	}

	@ExceptionHandler(QuestionException.class)
	public ErrorResponse handleQuestionIdNotFoundException(QuestionException e, WebRequest request) {
		return new ErrorResponse(new Date().toString(), HttpStatus.OK.toString(), e.getMessage(),
				request.getDescription(false));
	}

	@ExceptionHandler(QuizException.class)
	public ErrorResponse handleQuizNotFoundException(QuizException e, WebRequest request) {
		return new ErrorResponse(new Date().toString(), HttpStatus.OK.toString(), e.getMessage(),
				request.getDescription(false));
	}

	@ExceptionHandler(UserException.class)
	public ErrorResponse handleDuplicateQuestionFoundException(UserException e, WebRequest request) {
		return new ErrorResponse(new Date().toString(), HttpStatus.OK.toString(), e.getMessage(),
				request.getDescription(false));
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ErrorResponse handleAccessDeniedException(AccessDeniedException e, WebRequest request) {
		return new ErrorResponse(new Date().toString(), HttpStatus.FORBIDDEN.toString(), e.getMessage(),
				request.getDescription(false));

	}

	@ExceptionHandler(RuntimeException.class)
	public ErrorResponse handleRunTimeException(RuntimeException e, WebRequest request) {
		return new ErrorResponse(new Date().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(),
				request.getDescription(false));

	}

}
