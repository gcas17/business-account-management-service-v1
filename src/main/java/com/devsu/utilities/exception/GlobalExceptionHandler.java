package com.devsu.utilities.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseStatusExceptionHandler {

  @ExceptionHandler(CustomApiException.class)
  public ResponseEntity<ErrorMessage> handleCustomApiException(CustomApiException ex, ServerWebExchange exchange) {
    ErrorMessage errorResponse = ErrorMessage.builder()
        .code(ex.getCode())
        .description(ex.getDescription())
        .build();
    return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
  }
}