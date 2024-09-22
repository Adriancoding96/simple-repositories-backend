package com.adrian.simple_repositories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.simple_repositories.dto.FolderDTO;
import com.adrian.simple_repositories.service.FolderService;

import java.util.List;


@RestController
@RequestMapping("/folder")
public class FolderController {


  private final FolderService folderService;

  @Autowired
  public FolderController(FolderService folderService) {
    this.folderService = folderService;
  }

  @GetMapping
  public ResponseEntity<List<FolderDTO>> getAllFolders() {
    List<FolderDTO> folders = folderService.getAllFolders();
    return ResponseEntity.ok(folders);

  }  

  @GetMapping("/{parentFolderId}")
  public ResponseEntity<List<FolderDTO>> getAllSubFolders(@PathVariable Long parentFolderId) {
    List<FolderDTO> subFolders = folderService.getAllSubFolders(parentFolderId);
    return ResponseEntity.ok(subFolders);    
  }

  @GetMapping("/{FolderId}")
  public ResponseEntity<FolderDTO> getFolderById(@PathVariable Long folderId) {
    FolderDTO folder = folderService.getFolderById(folderId);
    return ResponseEntity.ok(folder);
  }

  @PostMapping
  public ResponseEntity<FolderDTO> createFolder(@RequestBody FolderDTO folderDTO, @PathVariable Long projectId) {
    FolderDTO folder = folderService.createFolder(folderDTO, projectId);
    return ResponseEntity.status(HttpStatus.CREATED).body(folder);
  }

}
