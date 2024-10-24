package com.adrian.simple_repositories.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.dto.auth.LoginRequestDTO;
import com.adrian.simple_repositories.dto.auth.LoginResponseDTO;
import com.adrian.simple_repositories.security.JwtHelper;
import com.adrian.simple_repositories.service.AuthenticationService;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;

  @Autowired
  public AuthenticationServiceImpl(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public LoginResponseDTO authenticateUser(LoginRequestDTO request, HttpServletResponse response) {
    //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    //String token = JwtHelper.generateToken(request.getEmail());
    return null;

  }

}
