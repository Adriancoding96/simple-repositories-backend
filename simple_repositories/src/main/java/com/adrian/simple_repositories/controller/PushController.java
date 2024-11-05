package com.adrian.simple_repositories.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.simple_repositories.dto.push.PushDTO;
import com.adrian.simple_repositories.dto.push.PushResponseDTO;
import com.adrian.simple_repositories.service.PushService;

@RestController
@RequestMapping("/push")
public class PushController {

  private final PushService pushService;

  @Autowired
  public PushController(PushService pushService) {
    this.pushService = pushService;
  }

  /*
   * Creates a new Push
   *
   * @Param pushDTO: DTO containing push data (project/directory/file)
   * @return pushResponseDTO: returns DTO containing push response data
   */
  @PostMapping
  public ResponseEntity<PushResponseDTO> createPush(@RequestBody PushDTO pushDTO) {
    PushResponseDTO pushResponse = pushService.createPush(pushDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(pushResponse);
  }

  /*
   * Retrieves push as DTO by id (TODO needs to be changed to uuid)
   *
   * @Param id: ID of push
   * @return pushDTO: returns DTO containing push response data (TODO needs to be changed to information DTO)
   */
  @GetMapping("/{id}")
  public ResponseEntity<PushDTO> getPushById(@PathVariable Long id) {
    return ResponseEntity.ok(pushService.getPushDTOById(id));
  }

  /*
   * Retrieves all pushes
   *
   * @Return pushDTOs: returns list of DTOs containing data of pushes
   */
  @GetMapping
  public ResponseEntity<List<PushDTO>> getAllPushes() {
    return ResponseEntity.ok(pushService.getAllPushesAsDTOs());
  }
}
