package com.adrian.simple_repositories.service;

import com.adrian.simple_repositories.dto.auth.SignupRequestDTO;
import com.adrian.simple_repositories.model.User;

public interface UserService {
  User getUserByEmail(String email);
  void signup(SignupRequestDTO signupRequestDTO);
}
