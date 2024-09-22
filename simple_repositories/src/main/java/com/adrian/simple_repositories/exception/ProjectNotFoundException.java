package com.adrian.simple_repositories.exception;

public class ProjectNotFoundException extends RuntimeException { 
  public ProjectNotFoundException(String message) {
    super(message);
  }
}
