package com.adrian.simple_repositories.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.project.ProjectDTO;
import com.adrian.simple_repositories.dto.project.ProjectFullDTO;
import com.adrian.simple_repositories.dto.project.ProjectInformationDTO;
import com.adrian.simple_repositories.dto.UniqueIdentifierDTO;
import com.adrian.simple_repositories.model.Project;

@Component
public class ProjectMapper {

  private final FolderMapper folderMapper;

  @Autowired
  public ProjectMapper(FolderMapper folderMapper) {
    this.folderMapper = folderMapper;
  }

  public ProjectFullDTO toFullDTO(Project project) {
    if(project == null) return null; //TODO implement exception

    ProjectFullDTO dto = new ProjectFullDTO();
    dto.setProjectName(project.getProjectName());
    dto.setProjectInformation(project.getProjectInformation());
    dto.setFolders(project.getFolders().stream()
      .map(folderMapper::toFullDTO)
      .collect(Collectors.toList()));

    return dto;
  }

  public ProjectDTO toDTO(Project project) {
    ProjectDTO dto = new ProjectDTO();
    dto.setProjectName(project.getProjectName());
    dto.setProjectInformation(project.getProjectInformation());
    return dto;
  } 


  public Project toEntity(ProjectDTO dto) {
    if(dto == null) return null;

    Project project = new Project();
    project.setProjectName(dto.getProjectName());
    project.setProjectInformation(dto.getProjectInformation());
    
    return project;
  }

  public ProjectInformationDTO toInfoDTO(Project project) {
    ProjectInformationDTO dto = new ProjectInformationDTO();
    dto.setProjectName(project.getProjectName());
    dto.setProjectInformation(project.getProjectInformation());
    //File types set in service
    
    return dto;
  }

  public UniqueIdentifierDTO toIdentifierDTO(Project project) {
    return new UniqueIdentifierDTO("project", project.getUuid());
  }

}
