package com.adrian.simple_repositories.exception;

public class ExistingUserException extends RuntimeException {
  public ExistingUserException(String message) {
    super(message);
  }
}
