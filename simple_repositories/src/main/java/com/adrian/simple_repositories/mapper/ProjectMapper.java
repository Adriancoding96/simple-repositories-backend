package com.adrian.simple_repositories.mapper;

import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.ProjectDTO;
import com.adrian.simple_repositories.model.Project;

@Component
public class ProjectMapper {

  public ProjectDTO toDTO(Project project) {
    if(project == null) return null;

    ProjectDTO dto = new ProjectDTO();
    dto.setId(project.getId());
    dto.setProjectName(project.getProjectName());
    dto.setProjectInformation(project.getProjectInformation());

    return dto;
  }

  public Project toEntity(ProjectDTO dto) {
    if(dto == null) return null;

    Project project = new Project();
    project.setId(dto.getId());
    project.setProjectName(dto.getProjectName());
    project.setProjectInformation(dto.getProjectInformation());
    
    return project;
  }
}
