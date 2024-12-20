package com.adrian.simple_repositories.controller;

import java.util.List;

import com.adrian.simple_repositories.dto.UniqueIdentifierDTO;
import com.adrian.simple_repositories.dto.repo.RepoDTO;
import com.adrian.simple_repositories.dto.repo.RepoFullDTO;
import com.adrian.simple_repositories.dto.repo.RepoIdentifierRequestDTO;
import com.adrian.simple_repositories.dto.repo.RepoInformationDTO;
import com.adrian.simple_repositories.dto.repo.RepoSetupDTO;
import com.adrian.simple_repositories.service.RepoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repo")
public class RepoController {
  
  private RepoService repoService;

  @Autowired
  public RepoController(RepoService repoService) {
    this.repoService = repoService;
  }

  @GetMapping("/test")
  public String testEndpoint() {
    System.out.println("Test endpoint called");
    return "TEST";
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<RepoFullDTO> getRepoByUuid(@PathVariable String uuid) {
    return ResponseEntity.ok(repoService.getRepoAsDTOByUuid(uuid));
  }

  @GetMapping("/info")
  public ResponseEntity<List<RepoInformationDTO>> getAllreposInfo() {
    return ResponseEntity.ok(repoService.getAllReposAsInfoDTOs());
  }

  @GetMapping("/identifier")
  public ResponseEntity<UniqueIdentifierDTO> getRepoIdentidier(@RequestBody RepoIdentifierRequestDTO request) {
    return ResponseEntity.ok(repoService.getRepoIdentiferByRepoNameAndUserEmail(request));
  }

  @PostMapping("/new")
  public ResponseEntity<RepoDTO> newRepo(@RequestBody RepoSetupDTO setupDTO) {
    System.out.println("Repo endpoint called");
    return ResponseEntity.ok(repoService.createEmptyRepo(setupDTO));  
  }

/*
  @PutMapping("/{uuid}")
  public ResponseEntity<RepoDTO> updateRepo(@RequestBody RepoUpdateDTO updateDTO, @PathVariable String uuid) {
    return ResponseEntity.ok(repoService.updateRepo(updateDTO, uuid));
  }
*/

  @DeleteMapping("/{uuid}")
  public ResponseEntity<Void> deleteRepoByUuid(@PathVariable String uuid) {
    repoService.deleteRepoByUuid(uuid);
    return ResponseEntity.noContent().build();
  }
}
