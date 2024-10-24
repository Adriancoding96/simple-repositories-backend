package com.adrian.simple_repositories.exception;

public class PushNotFoundException extends RuntimeException {
  public PushNotFoundException(String message) {
    super(message);
  }
}
