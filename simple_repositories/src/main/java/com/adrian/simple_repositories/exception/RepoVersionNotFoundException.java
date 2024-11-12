package com.adrian.simple_repositories.exception;


public class RepoVersionNotFoundException extends RuntimeException {
  public RepoVersionNotFoundException(String message) {
    super(message);
  }
}
