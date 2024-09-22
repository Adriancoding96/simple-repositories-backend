package com.adrian.simple_repositories.util;

import java.util.Arrays;
import java.util.List;

import com.adrian.simple_repositories.response.ApiResponse;

public class ResponseUtil {

  public static <T> ApiResponse<T> success(T data, String message, String path) {
    ApiResponse<T> response = new ApiResponse<>();
    response.setSuccess(true);
    response.setMessage(message);
    response.setData(data);
    response.setErrors(null);
    response.setErrorCode(0);
    response.setTimeStamp(System.currentTimeMillis());
    response.setPath(path);
  
    return response;
  }

  public static <T> ApiResponse<T> error(List<String> errors, String message, int errorCode, String path) {
    ApiResponse<T> response = new ApiResponse<>();
    response.setSuccess(false);
    response.setMessage(message);
    response.setData(null);
    response.setErrors(errors);
    response.setErrorCode(errorCode);
    response.setTimeStamp(System.currentTimeMillis());
    response.setPath(path);

    return response;
  }

  public static <T> ApiResponse<T> error(String error, String message, int errorCode, String path) {
    return error(Arrays.asList(error), message, errorCode, path);
  }
 
}
