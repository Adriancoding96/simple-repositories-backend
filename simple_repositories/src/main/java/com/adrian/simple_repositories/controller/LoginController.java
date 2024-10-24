package com.adrian.simple_repositories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.simple_repositories.dto.auth.LoginRequestDTO;
import com.adrian.simple_repositories.dto.auth.LoginResponseDTO;
import com.adrian.simple_repositories.dto.auth.UserDetailDTO;
import com.adrian.simple_repositories.security.JwtHelper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

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

  @CrossOrigin
  @PostMapping(value = "/auth")
  public ResponseEntity<UserDetailDTO> authenticateUser(@RequestBody LoginRequestDTO request, HttpServletResponse response) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    String token = JwtHelper.generateToken(request.getEmail());
    
    Cookie cookie = new Cookie("token", token);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(24 * 60 * 60);
    response.addCookie(cookie);

    UserDetailDTO userDetailDTO = new UserDetailDTO(request.getEmail());
    return ResponseEntity.ok(userDetailDTO);
  }


}
