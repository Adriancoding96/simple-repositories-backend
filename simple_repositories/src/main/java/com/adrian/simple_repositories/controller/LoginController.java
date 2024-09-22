package com.adrian.simple_repositories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.simple_repositories.dto.LoginRequestDTO;
import com.adrian.simple_repositories.dto.LoginResponseDTO;
import com.adrian.simple_repositories.security.JwtHelper;

@RestController
public class LoginController {

  private AuthenticationManager authenticationManager;

  @Autowired
  public LoginController(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @PostMapping(value = "/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    String token = JwtHelper.generateToken(request.getEmail());
    return ResponseEntity.ok(new LoginResponseDTO(request.getEmail(), token));
  }

}
