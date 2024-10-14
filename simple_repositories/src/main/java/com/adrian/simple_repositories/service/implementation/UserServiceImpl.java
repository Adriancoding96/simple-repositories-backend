package com.adrian.simple_repositories.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.dto.SignupRequestDTO;
import com.adrian.simple_repositories.model.User;
import com.adrian.simple_repositories.repository.UserRepository;
import com.adrian.simple_repositories.service.UserService;
import com.adrian.simple_repositories.exception.ExistingUserException;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional
  public void signup(SignupRequestDTO signupRequestDTO) {
    String email = signupRequestDTO.getEmail();
    Optional<User> existingUser = userRepository.findByEmail(email);
    if(existingUser.isPresent()) {
      throw new ExistingUserException("User with email: " + email + " already exists");
    }
    
    String hashedPassword = passwordEncoder.encode(signupRequestDTO.getPassword());
    User user = new User(signupRequestDTO.getName(), email, hashedPassword);
    userRepository.save(user);
  }
}
