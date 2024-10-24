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

import com.adrian.simple_repositories.service.FileService;
import com.adrian.simple_repositories.dto.file.FileDTO;
import com.adrian.simple_repositories.dto.file.FileUpdateDTO;

@RestController
@RequestMapping("/file")
public class FileController {

  private final FileService fileService;

  @Autowired
  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<FileDTO> getFileById(@PathVariable String uuid) {
    return ResponseEntity.ok(fileService.getFileAsDTOByUuid(uuid));
  }

  @GetMapping("/project/{uuid}")
  public ResponseEntity<List<FileDTO>> getAllFilesByProjectUuid(@PathVariable String uuid) {
    return ResponseEntity.ok(fileService.getAllFilesAsDTOsByProjectUuid(uuid));
  }

  @PutMapping("/update/{uuid}")
  public ResponseEntity<FileDTO> updateFile(@RequestBody FileUpdateDTO updateDTO, @PathVariable String uuid) {
    return ResponseEntity.ok(fileService.updateFile(updateDTO, uuid));
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<Void> deleteFile(@PathVariable String uuid) {
    fileService.deleteFileByUuid(uuid);
    return ResponseEntity.noContent().build();
  }

}
