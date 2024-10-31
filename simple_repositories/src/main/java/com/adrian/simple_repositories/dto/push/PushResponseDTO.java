package com.adrian.simple_repositories.dto.push;

public class PushResponseDTO {
  
  private boolean success;
  
  private String message;
  
  private String uuid;


  public PushResponseDTO() {

  }

  public PushResponseDTO(boolean success, String message, String uuid) {
    this.success = success;
    this.message = message;
    this.uuid = uuid;
  }

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  public String getUuid() {
    return uuid;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

}
