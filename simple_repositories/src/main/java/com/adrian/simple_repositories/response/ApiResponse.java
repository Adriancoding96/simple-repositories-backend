package com.adrian.simple_repositories.response;

import java.util.List;

/*
 * Probably not going to use this, better to stick to HttpResponse
 * */
public class ApiResponse<T> {
  
  private boolean success;
  private String message;
  private T data;
  private List<String> errors;
  private int errorCode;
  private long timeStamp;
  private String path;

  public ApiResponse() {

  }

  public ApiResponse(boolean success, String message, T data, List<String> errors, int errorCode, long timeStamp, String path) {
    this.success = success;
    this.message = message;
    this.data = data;
    this.errors = errors;
    this.errorCode = errorCode;
    this.timeStamp = timeStamp;
    this.path = path;
  }

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }

  public List<String> getErrors() {
    return errors;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public long getTimeStamp() {
    return timeStamp;
  }

  public String getPath() {
    return path;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setData(T data) {
    this.data = data;
  }

  public void setErrors(List<String> errors) {
    this.errors = errors;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public void setTimeStamp(long timeStamp) {
    this.timeStamp = timeStamp;
  }

  public void setPath(String path) {
    this.path = path;
  }
  
}
