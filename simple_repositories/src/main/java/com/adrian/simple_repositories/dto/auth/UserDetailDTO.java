package com.adrian.simple_repositories.dto.auth;

public class UserDetailDTO {
  
  private String email;

  public UserDetailDTO(String email) {
    this.email = email;
  } 

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
} 
