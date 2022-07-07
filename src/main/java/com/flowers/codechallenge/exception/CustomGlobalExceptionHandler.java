/**
 * This is a centralized exception handler class to catch
 * unexpected exceptions across the application
 * and set proper error details then throws to
 * the consumer.
 *
 * @author Nagendra Kumar Aluru
 */
package com.flowers.codechallenge.exception;

import java.util.Date;

import com.flowers.codechallenge.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.function.client.WebClientResponseException;




@ControllerAdvice
public class CustomGlobalExceptionHandler {
	

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {

		ErrorResponse errorResponse = ErrorResponse
				.builder()
				.timestamp(new Date())
				.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
				.reason("Internal server error!!")
				.error(ex.getMessage())
				.requestedUri(request.getDescription(false))
				.build();

		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(errorResponse);
	}


	@ExceptionHandler(WebClientResponseException.class)
	protected ResponseEntity<ErrorResponse> handleWebClientResponseException(WebClientResponseException ex, WebRequest request) {

		ErrorResponse errorResponse = ErrorResponse
				.builder()
				.timestamp(new Date())
				.statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
				.status(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
				.reason("External Service Unavailable or Not Reachable!!")
				.error(ex.getMessage())
				.requestedUri(request.getDescription(false))
				.build();

		return ResponseEntity
				.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(errorResponse);
	}


	@ExceptionHandler(ArrayIndexOutOfBoundsException.class)
	protected ResponseEntity<ErrorResponse> handleArrayIndexOutOfBoundException(ArrayIndexOutOfBoundsException ex, WebRequest request) {

		ErrorResponse errorResponse = ErrorResponse
				.builder()
				.timestamp(new Date())
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.status(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.reason(ex.getMessage())
				.error(ex.getMessage())
				.requestedUri(request.getDescription(false))
				.build();

		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(errorResponse);
	}


	@ExceptionHandler({HttpMessageNotReadableException.class})
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {

		ErrorResponse errorResponse = ErrorResponse
				.builder()
				.timestamp(new Date())
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.status(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.reason("Request body having issue. Please check!!")
				.error(ex.getMessage())
				.requestedUri(request.getDescription(false))
				.build();
		
        return ResponseEntity
        		.status(HttpStatus.BAD_REQUEST)
        		.body(errorResponse);
	}
	
	
	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {

		ErrorResponse errorResponse = ErrorResponse
				.builder()
				.timestamp(new Date())
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.status(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.reason(ex.getName() + " should be of type " + ex.getRequiredType().getName())
				.error(ex.getMessage())
				.requestedUri(request.getDescription(false))
				.build();
        return ResponseEntity
        		.status(HttpStatus.BAD_REQUEST)
        		.body(errorResponse);
	}
	
	
	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, WebRequest request) {

		ErrorResponse errorResponse = ErrorResponse
				.builder()
				.timestamp(new Date())
				.statusCode(HttpStatus.METHOD_NOT_ALLOWED.value())
				.status(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
				.reason("Requested method not supported. Please check!!")
				.error(ex.getMessage())
				.requestedUri(request.getDescription(false))
				.build();

        return ResponseEntity
        		.status(HttpStatus.METHOD_NOT_ALLOWED)
        		.body(errorResponse);
	}

	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {

		ErrorResponse errorResponse = ErrorResponse
				.builder()
				.timestamp(new Date())
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.status(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.reason(ex.getBindingResult().getFieldError().getDefaultMessage())
				.error(ex.getMessage())
				.requestedUri(request.getDescription(false))
				.build();

        return ResponseEntity
        		.status(HttpStatus.BAD_REQUEST)
        		.body(errorResponse);
	}
	

	@ExceptionHandler({HttpMediaTypeNotSupportedException.class})
	public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, WebRequest request) {

		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are application/json");

		ErrorResponse errorResponse = ErrorResponse
				.builder()
				.timestamp(new Date())
				.statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
				.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())
				.reason(ex.getMessage())
				.error(builder.toString())
				.requestedUri(request.getDescription(false))
				.build();

        return ResponseEntity
        		.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
        		.body(errorResponse);
	}
	

	@ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
	public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotAcceptableException (HttpMediaTypeNotAcceptableException  ex, WebRequest request) {

		ErrorResponse errorResponse = ErrorResponse
				.builder()
				.timestamp(new Date())
				.statusCode(HttpStatus.NOT_ACCEPTABLE.value())
				.status(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase())
				.reason(ex.getMessage())
				.error(ex.getMessage())
				.requestedUri(request.getDescription(false))
				.build();

        return ResponseEntity
        		.status(HttpStatus.NOT_ACCEPTABLE)
        		.body(errorResponse);
	}
}
