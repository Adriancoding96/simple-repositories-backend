package com.adrian.simple_repositories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.simple_repositories.dto.auth.SignupRequestDTO;
import com.adrian.simple_repositories.service.UserService;

@RestController
public class SignupController {

  private UserService userService;

  @Autowired
  public SignupController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signup")
  public ResponseEntity<Void> signup(@RequestBody SignupRequestDTO signupRequestDTO) {
    userService.signup(signupRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
