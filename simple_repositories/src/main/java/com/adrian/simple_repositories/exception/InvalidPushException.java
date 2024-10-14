package com.adrian.simple_repositories.exception;

public class InvalidPushException extends RuntimeException {
  public InvalidPushException(String message) {
    super(message);
  }
} 
