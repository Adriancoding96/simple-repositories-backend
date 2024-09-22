package com.adrian.simple_repositories.exception;

public class FolderNotFoundException extends RuntimeException {
  public FolderNotFoundException(String message) {
    super(message);
  }
}
