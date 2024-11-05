package com.adrian.simple_repositories.exception;

public class DirectoryNotFoundException extends RuntimeException {
  public DirectoryNotFoundException(String message) {
    super(message);
  }
}
