package com.adrian.simple_repositories.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.adrian.simple_repositories.exception.RepoNotFoundException;
import com.adrian.simple_repositories.exception.RepoVersionNotFoundException;
import com.adrian.simple_repositories.exception.PushNotFoundException;
import com.adrian.simple_repositories.exception.DirectoryNotFoundException;
import com.adrian.simple_repositories.exception.InvalidPushException;
import com.adrian.simple_repositories.exception.FileNotFoundException;
import com.adrian.simple_repositories.exception.AccessDeniedException;
import com.adrian.simple_repositories.exception.BranchNotFoundException;
import com.adrian.simple_repositories.exception.UserNotFoundException;
import com.adrian.simple_repositories.exception.ExistingUserException;

@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
    Map<String, String> map = new HashMap<>();
    exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
      map.put(fieldError.getField(), fieldError.getDefaultMessage());
    });
    return map;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(RepoNotFoundException.class)
  public Map<String, String> handleRepoNotFoundException(RepoNotFoundException exception) {
    Map<String, String> map = new HashMap<>();
    map.put("errorMessage", exception.getMessage());
    return map;
  }
  
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(DirectoryNotFoundException.class)
  public Map<String, String> handleDirectoryNotFoundException(DirectoryNotFoundException exception) {
    Map<String, String> map = new HashMap<>();
    map.put("errorMessage", exception.getMessage());
    return map;
  }
  
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(FileNotFoundException.class)
  public Map<String, String> handleFileNotFoundException(FileNotFoundException exception) {
    Map<String, String> map = new HashMap<>();
    map.put("errorMessage", exception.getMessage());
    return map;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(BranchNotFoundException.class)
  public Map<String, String> handleBranchNotFoundException(BranchNotFoundException exception) {
    Map<String, String> map = new HashMap<>();
    map.put("errorMessage", exception.getMessage());
    return map;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(UserNotFoundException.class)
  public Map<String, String> handleUserNotFoundException(UserNotFoundException exception) {
    Map<String, String> map = new HashMap<>();
    map.put("errorMessage", exception.getMessage());
    return map;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(PushNotFoundException.class)
  public Map<String, String> handlePushNotFoundException(PushNotFoundException exception) {
    Map<String, String> map = new HashMap<>();
    map.put("errorMessage", exception.getMessage());
    return map;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(RepoVersionNotFoundException.class)
  public Map<String, String> handleRepoNotFoundException(PushNotFoundException exception) {
    Map<String, String> map = new HashMap<>();
    map.put("errorMessage", exception.getMessage());
    return map;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(ExistingUserException.class)
  public Map<String, String> handleExistingUserException(ExistingUserException exception) {
    Map<String, String> map = new HashMap<>();
    map.put("errorMessage", exception.getMessage());
    return map;
  }
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(AccessDeniedException.class)
  public Map<String, String> handleAccessDenied(AccessDeniedException exception) {
    Map<String, String> map = new HashMap<>();
    map.put("errorMessage", exception.getMessage());
    return map;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(InvalidPushException.class)
  public Map<String, String> handleInvalidPushException(InvalidPushException exception) {
    Map<String, String> map = new HashMap<>();
    map.put("errorMessage", exception.getMessage());
    return map;
  }
}
