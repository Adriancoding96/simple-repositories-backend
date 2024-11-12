package com.adrian.simple_repositories.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.repository.UserRepository;
import com.adrian.simple_repositories.model.User;
import com.adrian.simple_repositories.dto.auth.UserDetailDTO;
import com.adrian.simple_repositories.exception.UserNotFoundException;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

  private UserRepository userRepository;

  @Autowired
  public UserDetailServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserDetails loadUserByUsername(String email) {
    User user = getUserByEmail(email); 
    return org.springframework.security.core.userdetails.User.builder()
      .username(user.getEmail())
      .password(user.getPassword())
      .build();
  }

  public UserDetailDTO getUserAsDTOByEmail(String email) {
    User user = getUserByEmail(email); 
    return new UserDetailDTO(user.getEmail());
  }

  private User getUserByEmail(String email) {
    return userRepository.findByEmail(email)
      .orElseThrow(() -> new UserNotFoundException("Could not find user with email: " + email));
  }

} 
