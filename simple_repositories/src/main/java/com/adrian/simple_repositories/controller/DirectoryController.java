package com.adrian.simple_repositories.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.simple_repositories.dto.directory.DirectoryFullDTO;
import com.adrian.simple_repositories.dto.directory.DirectoryUpdateDTO;
import com.adrian.simple_repositories.dto.directory.DirectoryDTO;
import com.adrian.simple_repositories.service.DirectoryService;

@RestController
@RequestMapping("/directory")
public class DirectoryController {

  private final DirectoryService directoryService;

  @Autowired
  public DirectoryController(DirectoryService directoryService) {
    this.directoryService = directoryService;
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<DirectoryFullDTO> getDirectoryById(@PathVariable String uuid) {
    return ResponseEntity.ok(directoryService.getDirectoryAsDTOByUuid(uuid));
  }

  @GetMapping("/repo/{uuid}")
  public ResponseEntity<List<DirectoryDTO>> getAllDirectoriesByRepoUuid(@PathVariable String uuid) {
    return ResponseEntity.ok(directoryService.getAllDirectoriesAsDTOsByRepoUuid(uuid));
  }

  @PutMapping("/{uuid}")
  public ResponseEntity<DirectoryDTO> updateDirectory(@RequestBody DirectoryUpdateDTO updateDTO, @PathVariable String uuid) {
    return ResponseEntity.ok(directoryService.updateDirectory(uuid, updateDTO)); 
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<Void> deleteRepo(@PathVariable String uuid) {
    directoryService.deleteDirectoryByUuid(uuid);
    return ResponseEntity.noContent().build();
  }
}
