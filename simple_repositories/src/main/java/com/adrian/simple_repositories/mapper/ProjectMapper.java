package com.adrian.simple_repositories.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.dto.ProjectDTO;
import com.adrian.simple_repositories.dto.ProjectFullDTO;
import com.adrian.simple_repositories.exception.FolderNotFoundException;
import com.adrian.simple_repositories.model.Folder;
import com.adrian.simple_repositories.model.Project;
import com.adrian.simple_repositories.repository.FolderRepository;
import com.adrian.simple_repositories.util.ValidationUtils;

@Service
public class ProjectMapper {
  
  private FolderRepository folderRepository; 
  private FolderMapper folderMapper;


  @Autowired
  public ProjectMapper(FolderRepository folderRepository, FolderMapper folderMapper) {
    this.folderRepository = folderRepository;
    this.folderMapper = folderMapper;
  }

  public ProjectDTO toDTO(Project project) {
    ValidationUtils.validateObject(project);

    ProjectDTO projectDTO = new ProjectDTO();
    projectDTO.setId(project.getId());
    projectDTO.setProjectName(project.getProjectName());
    projectDTO.setFolders(getFolderIds(project.getFolders()));
    return projectDTO;
  }

  public Project toEntity(ProjectDTO projectDTO) {
    ValidationUtils.validateObject(projectDTO);

    Project project = new Project();
    project.setId(projectDTO.getId());
    project.setProjectName(projectDTO.getProjectName());
    project.setFolders(getFolders(projectDTO.getId()));
    return project;
  }

  public ProjectFullDTO toFullDTO(Project project) {
    ValidationUtils.validateObject(project);

    ProjectFullDTO projectFullDTO = new ProjectFullDTO();
    projectFullDTO.setId(project.getId());
    projectFullDTO.setProjectName(project.getProjectName());
    projectFullDTO.setFolders(project.getFolders()
      .stream()
      .map(folderMapper::toFullDTO)
      .collect(Collectors.toList()));
    return projectFullDTO;

  }

  public Project toEntityFromFullDTO(ProjectFullDTO projectFullDTO) {
    ValidationUtils.validateObject(projectFullDTO);

    Project project = new Project();
    project.setId(projectFullDTO.getId());
    project.setProjectName(projectFullDTO.getProjectName());
    project.setFolders(projectFullDTO.getFolders()
      .stream()
      .map(folderMapper::toEntityFromFullDTO)
      .collect(Collectors.toList()));
  
    return project;
  } 

  private List<Long> getFolderIds(List<Folder> folders) {
    return folders.stream()
      .map(Folder::getId)
      .collect(Collectors.toList());
  }

  private List<Folder> getFolders(Long projectId) {
    List<Folder> folders = folderRepository.findAllFoldersByProjectId(projectId) 
      .orElseThrow(() -> new FolderNotFoundException("Could not find folders with the project id: " + projectId));
    return folders;
  }
}
