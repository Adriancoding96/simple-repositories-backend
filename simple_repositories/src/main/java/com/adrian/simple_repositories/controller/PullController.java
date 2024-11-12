package com.adrian.simple_repositories.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.simple_repositories.dto.pull.PullRequestDTO;
import com.adrian.simple_repositories.dto.pull.PullResponseDTO;
import com.adrian.simple_repositories.service.PullService;

@RestController
@RequestMapping("/pull")
public class PullController {

  private final PullService pullService;

  public PullController(PullService pullService) {
    this.pullService = pullService;
  }

  @GetMapping
  public ResponseEntity<PullResponseDTO> getRepo(@RequestBody PullRequestDTO requestDTO) {
    return ResponseEntity.ok(pullService.pullRepo(requestDTO));
  } 

}
