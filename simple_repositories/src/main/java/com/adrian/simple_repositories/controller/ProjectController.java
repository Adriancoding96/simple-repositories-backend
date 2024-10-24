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

import com.adrian.simple_repositories.dto.project.ProjectDTO;
import com.adrian.simple_repositories.dto.project.ProjectFullDTO;
import com.adrian.simple_repositories.dto.project.ProjectIdentifierRequestDTO;
import com.adrian.simple_repositories.dto.project.ProjectInformationDTO;
import com.adrian.simple_repositories.dto.project.ProjectUpdateDTO;
import com.adrian.simple_repositories.dto.UniqueIdentifierDTO;
import com.adrian.simple_repositories.service.ProjectService;

@RestController
@RequestMapping("/project")
public class ProjectController {
  
  private ProjectService projectService;

  @Autowired
  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<ProjectFullDTO> getProjectByUuid(@PathVariable String uuid) {
    return ResponseEntity.ok(projectService.getProjectAsDTOByUuid(uuid));
  }

  @GetMapping("/info")
  public ResponseEntity<List<ProjectInformationDTO>> getAllProjectsInfo() {
    return ResponseEntity.ok(projectService.getAllProjectsAsInfoDTOs());
  }

  @GetMapping("/identifier")
  public ResponseEntity<UniqueIdentifierDTO> getProjectIdentidier(@RequestBody ProjectIdentifierRequestDTO request) {
    return ResponseEntity.ok(projectService.getProjectIdentiferByProjectNameAndUserEmail(request));
  }

  @PutMapping("/{uuid}")
  public ResponseEntity<ProjectDTO> updateProject(@RequestBody ProjectUpdateDTO updateDTO, @PathVariable String uuid) {
    return ResponseEntity.ok(projectService.updateProject(updateDTO, uuid));
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<Void> deleteProjectrByUuid(@PathVariable String uuid) {
    projectService.deleteProjectByUuid(uuid);
    return ResponseEntity.noContent().build();
  }
}
