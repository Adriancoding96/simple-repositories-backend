package com.adrian.simple_repositories.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.adrian.simple_repositories.exception.ProjectNotFoundException;
import com.adrian.simple_repositories.exception.FolderNotFoundException;
import com.adrian.simple_repositories.exception.FileNotFoundException;
import com.adrian.simple_repositories.exception.AccessDeniedException;

//Class clarifys the response messages sent back when faulty controller requests are recognized
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
  @ExceptionHandler(ProjectNotFoundException.class)
  public Map<String, String> handleProjectNotFoundException(ProjectNotFoundException exception) {
    Map<String, String> map = new HashMap<>();
    map.put("errorMessage", exception.getMessage());
    return map;
  }
  
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(FolderNotFoundException.class)
  public Map<String, String> handleFolderNotFoundException(FolderNotFoundException exception) {
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
  @ExceptionHandler(FileNotFoundException.class)
  public Map<String, String> handleFileNotFoundException(FileNotFoundException exception) {
    Map<String, String> map = new HashMap<>();
    map.put("errorMessage", exception.getMessage());
    return map;
  }
}
