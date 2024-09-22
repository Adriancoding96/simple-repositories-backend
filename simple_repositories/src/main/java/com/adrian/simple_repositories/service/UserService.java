package com.adrian.simple_repositories.service;

import com.adrian.simple_repositories.dto.SignupRequestDTO;

public interface UserService {
  void signup(SignupRequestDTO signupRequestDTO);
}
