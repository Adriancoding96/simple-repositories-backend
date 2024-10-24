package com.adrian.simple_repositories.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.simple_repositories.dto.branch.BranchRequestDTO;
import com.adrian.simple_repositories.dto.branch.BranchDTO;

@RestController
@RequestMapping("/branch")
public class BranchController {

 
  @PostMapping
  ResponseEntity<BranchDTO> createNewBranch(@RequestBody BranchRequestDTO request) {
    return ResponseEntity.ok(null);
  } 
}
