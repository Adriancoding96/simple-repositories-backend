package com.adrian.simple_repositories.dto.auth;

public class LoginResponseDTO {

  String email;
  String token;

  public LoginResponseDTO() {

  }

  public LoginResponseDTO(String email, String token) {
    this.email = email;
    this.token = token;
  }

  public String getEmail() {
    return email;
  }

  public String getToken() {
    return token;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setToken(String token) {
    this.token = token;
  }


}
