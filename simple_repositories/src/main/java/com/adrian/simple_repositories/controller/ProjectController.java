package com.adrian.simple_repositories.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adrian.simple_repositories.dto.ProjectDTO;
import com.adrian.simple_repositories.dto.ProjectFullDTO;
import com.adrian.simple_repositories.service.ProjectService;

@RestController
@RequestMapping("/project")
public class ProjectController {
  
  private ProjectService projectService;

  @Autowired
  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @PostMapping("/full")
  public ResponseEntity<ProjectFullDTO> createFullProject(@RequestBody ProjectFullDTO projectFullDTO) {
    ProjectFullDTO savedProject = projectService.createFullProject(projectFullDTO);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(savedProject.getId())
      .toUri();
    return ResponseEntity.created(location).body(savedProject);
  }

  @PostMapping
  public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
    ProjectDTO savedProject = projectService.createProject(projectDTO);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(savedProject.getId())
      .toUri();
    return ResponseEntity.created(location).body(savedProject);
  }

  @GetMapping
  public ResponseEntity<List<ProjectDTO>> getAllProjects() {
    return ResponseEntity.ok(projectService.getAllProjects());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
    return ResponseEntity.ok(projectService.getProjectById(id));
  }

  @PutMapping
  public ResponseEntity<ProjectDTO> updateProjectById(@RequestBody ProjectDTO projectDTO) {
    return ResponseEntity.ok(projectService.updateProject(projectDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProjectById(@PathVariable Long id) {
    return ResponseEntity.noContent().build();
  }

}
