package com.adrian.simple_repositories.exception;

public class RepoNotFoundException extends RuntimeException { 
  public RepoNotFoundException(String message) {
    super(message);
  }
}
