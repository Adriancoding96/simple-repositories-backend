package com.adrian.simple_repositories.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.repository.UserRepository;
import com.adrian.simple_repositories.model.User;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

  private UserRepository userRepository;

  @Autowired
  public UserDetailServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) {
    User user = userRepository.findByEmail(email) //TODO Implement NotFoundException
      .orElseThrow(() -> new RuntimeException("User does not exist with email: " + email));
  
    return org.springframework.security.core.userdetails.User.builder()
      .username(user.getEmail())
      .password(user.getPassword())
      .build();
    
  }
} 
