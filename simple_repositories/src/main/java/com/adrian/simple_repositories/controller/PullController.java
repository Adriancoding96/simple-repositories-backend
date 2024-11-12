package com.adrian.simple_repositories.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.simple_repositories.dto.pull.PullRequestDTO;
import com.adrian.simple_repositories.dto.pull.PullResponseDTO;

@RestController
@RequestMapping("/pull")
public class PullController {

  @GetMapping
  public ResponseEntity<PullResponseDTO> getRepo(PullRequestDTO requestDTO) {
    String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    System.out.println("IMPORTANT: " + userEmail);
    return null;
  } 

}
