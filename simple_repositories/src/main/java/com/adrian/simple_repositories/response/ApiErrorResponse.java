package com.adrian.simple_repositories.response;

public class ApiErrorResponse {
  
  private int statusCode;
  private String message;

  public ApiErrorResponse() {

  }

  public ApiErrorResponse(int statusCode, String message) {
    this.statusCode = statusCode;
    this.message = message;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public String getMessage() {
    return message;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
