package com.devsu.utilities.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CustomApiException extends RuntimeException {

  private  final String code;

  private final String description;

  private final HttpStatus httpStatus;

}
