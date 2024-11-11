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

  /*
   * Retrieves file as DTO by uuid
   *
   * @param uuid: file uuid
   * @return fileDTO: returns DTO containing file data
   */
  @GetMapping("/{uuid}")
  public ResponseEntity<FileDTO> getFileById(@PathVariable String uuid) {
    return ResponseEntity.ok(fileService.getFileAsDTOByUuid(uuid));
  }

  /*
   * Retrieves file as DTO by repo uuid
   *
   * @param uuid: repo uuid
   * @return fileDTO: returns response entity containing DTO with file data 
   */
  @GetMapping("/repo/{uuid}")
  public ResponseEntity<List<FileDTO>> getAllFilesByRepoUuid(@PathVariable String uuid) {
    return ResponseEntity.ok(fileService.getAllFilesAsDTOsByRepoUuid(uuid));
  }

  /*
   * Updates file by uuid and returns updated file as a DTO
   *
   * @param uuid: file uuid
   * @return fileDTO: returns response entity containing DTO with update file data
   */
  @PutMapping("/{uuid}")
  public ResponseEntity<FileDTO> updateFile(@RequestBody FileUpdateDTO updateDTO, @PathVariable String uuid) {
    return ResponseEntity.ok(fileService.updateFile(updateDTO, uuid));
  }

  /*
   * Deletes file by uuid
   *
   * @param uuid: file uuid
   */
  @DeleteMapping("/{uuid}")
  public ResponseEntity<Void> deleteFile(@PathVariable String uuid) {
    fileService.deleteFileByUuid(uuid);
    return ResponseEntity.noContent().build();
  }

}
