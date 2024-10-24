package com.adrian.simple_repositories.controller;

import com.adrian.simple_repositories.dto.auth.UserDetailDTO;
import com.adrian.simple_repositories.service.implementation.UserDetailServiceImpl;
import com.adrian.simple_repositories.service.implementation.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserDetailServiceImpl userDetailServiceImpl;

  @Autowired
  public UserController(UserDetailServiceImpl userDetailServiceImpl) {
    this.userDetailServiceImpl = userDetailServiceImpl;
  }

  @GetMapping("/{email}")
  public ResponseEntity<UserDetailDTO> getUserByEmail(@PathVariable String email) {
    return ResponseEntity.ok(userDetailServiceImpl.getUserAsDTOByEmail(email));
  }
}
