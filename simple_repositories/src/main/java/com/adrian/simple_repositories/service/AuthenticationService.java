package com.adrian.simple_repositories.service;

import com.adrian.simple_repositories.dto.auth.LoginRequestDTO;
import com.adrian.simple_repositories.dto.auth.LoginResponseDTO;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
  LoginResponseDTO authenticateUser(LoginRequestDTO request, HttpServletResponse response); 
} 
