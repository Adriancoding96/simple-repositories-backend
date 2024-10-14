package com.adrian.simple_repositories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.simple_repositories.dto.PushDTO;
import com.adrian.simple_repositories.dto.PushResponseDTO;
import com.adrian.simple_repositories.service.PushService;

@RestController
@RequestMapping("/push")
public class PushController {

  private final PushService pushService;

  @Autowired
  public PushController(PushService pushService) {
    this.pushService = pushService;
  } 

  @PostMapping
  public ResponseEntity<PushResponseDTO> createPush(@RequestBody PushDTO pushDTO) {
    PushResponseDTO pushResponse = pushService.createPush(pushDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(pushResponse);
  }
}
