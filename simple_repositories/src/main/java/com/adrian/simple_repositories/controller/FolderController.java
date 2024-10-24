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

import com.adrian.simple_repositories.dto.folder.FolderFullDTO;
import com.adrian.simple_repositories.dto.folder.FolderUpdateDTO;
import com.adrian.simple_repositories.dto.folder.FolderDTO;
import com.adrian.simple_repositories.service.FolderService;

@RestController
@RequestMapping("/folder")
public class FolderController {

  private final FolderService folderService;

  @Autowired
  public FolderController(FolderService folderService) {
    this.folderService = folderService;
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<FolderFullDTO> getFolderById(@PathVariable String uuid) {
    return ResponseEntity.ok(folderService.getFolderAsDTOByUuid(uuid));
  }

  @GetMapping("/project/{uuid}")
  public ResponseEntity<List<FolderDTO>> getAllFoldersByProjectUuid(@PathVariable String uuid) {
    return ResponseEntity.ok(folderService.getAllFoldersAsDTOsByProjectUuid(uuid));
  }

  @PutMapping("/{uuid}")
  public ResponseEntity<FolderDTO> updateFolder(@RequestBody FolderUpdateDTO updateDTO, @PathVariable String uuid) {
    return ResponseEntity.ok(folderService.updateFolder(uuid, updateDTO)); 
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<Void> deleteProject(@PathVariable String uuid) {
    folderService.deleteFolderByUuid(uuid);
    return ResponseEntity.noContent().build();
  }
}
