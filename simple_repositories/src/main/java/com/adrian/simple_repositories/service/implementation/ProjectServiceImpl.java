package com.adrian.simple_repositories.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.assembler.ProjectAssembler;
import com.adrian.simple_repositories.dto.UniqueIdentifierDTO;
import com.adrian.simple_repositories.dto.project.ProjectDTO;
import com.adrian.simple_repositories.dto.project.ProjectFullDTO;
import com.adrian.simple_repositories.dto.project.ProjectIdentifierRequestDTO;
import com.adrian.simple_repositories.dto.project.ProjectInformationDTO;
import com.adrian.simple_repositories.dto.project.ProjectUpdateDTO;
import com.adrian.simple_repositories.exception.ProjectNotFoundException;
import com.adrian.simple_repositories.mapper.ProjectMapper;
import com.adrian.simple_repositories.model.File;
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.model.Project;
import com.adrian.simple_repositories.model.User;
import com.adrian.simple_repositories.repository.ProjectRepository;
import com.adrian.simple_repositories.service.ProjectService;

import jakarta.transaction.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectAssembler projectAssembler;
  private final ProjectMapper projectMapper;

  @Autowired
  public ProjectServiceImpl(ProjectRepository projectRepository, ProjectAssembler projectAssembler, ProjectMapper projectMapper) {
    this.projectRepository = projectRepository;
    this.projectAssembler = projectAssembler;
    this.projectMapper = projectMapper;
  }

  @Override
  @Transactional
  public Project createProjectFromPush(ProjectFullDTO projectDTO, User user) {
    Project project = projectAssembler.assemble(projectDTO, user);
    Project savedProject = projectRepository.save(project);
    if(savedProject != null) {
      System.out.println(savedProject.getProjectName());
      if(savedProject.getDirectories() == null) {
        System.out.println("PROJECTS FOLDERS ARE NULL");
      }
    }
    return savedProject;
  }

  @Override
  public Project getProjectById(Long projectId) {
    return projectRepository.findById(projectId)
      .orElseThrow(() -> new ProjectNotFoundException("Poject with id: " + projectId + " not found"));
  }

  @Override
  public Project getProjectByUuid(String uuid) {
    return projectRepository.findByUuid(uuid)
      .orElseThrow(() -> new ProjectNotFoundException("Could not find project with uuid: " + uuid));
  }

  @Override
  public ProjectFullDTO getProjectAsDTOByUuid(String uuid) {
    return projectMapper.toFullDTO(getProjectByUuid(uuid));
  }

  @Override
  public List<ProjectInformationDTO> getAllProjectsAsInfoDTOs() {
    List<Project> projects = projectRepository.findAll();
    if(projects == null || projects.isEmpty()) {
      throw new ProjectNotFoundException("Could not fetch all projects from database");
    }

    List<ProjectInformationDTO> infoDTOs = converToProjectInfoDTOs(projects); 
    for(int i = 0, n = projects.size(); i < n; i++) {
      List<String> extensions = getAllFileExtionsFromProject(projects.get(i));
      infoDTOs.get(i).addAllFileTypes(extensions);
    }

    return infoDTOs;
  }

  private List<ProjectInformationDTO> converToProjectInfoDTOs(List<Project> projects) {
    return projects.stream()
      .map(projectMapper::toInfoDTO)
      .collect(Collectors.toList());
  }

  private List<String> getAllFileExtionsFromProject(Project project) {
    List<String> extensions = new ArrayList<>();
    for(Directory directory : project.getDirectories()) {
      walkThroughDirectoryTree(directory, extensions);
    }

    return extensions;
  }

  private void walkThroughDirectoryTree(Directory directory, List<String> extensions) {
    if(directory.getFiles() != null || !directory.getFiles().isEmpty()) {
      getFileExtensionsFromDirectory(directory, extensions);
    } 

    if(directory.getDirectories() == null || directory.getDirectories().isEmpty()) return;
    for(Directory subDirectory : directory.getDirectories()) {
      walkThroughDirectoryTree(subDirectory, extensions);
    }
  } 

  private void getFileExtensionsFromDirectory(Directory directory, List<String> extensions) {
    for(File file : directory.getFiles()) {
      extensions.add(file.getExtension());
    }
  }

  @Override
  public Project getProjectByProjectNameAndUserEmail(ProjectIdentifierRequestDTO request) {
    return projectRepository.findProjectByProjectNameAndUserEmail(request.getProjectName(), request.getEmail())
      .orElseThrow(() -> new ProjectNotFoundException("Could not fetch project created by user: " + request.getEmail() + " with project name: " + request.getProjectName()));
  }

  @Override
  public UniqueIdentifierDTO getProjectIdentiferByProjectNameAndUserEmail(ProjectIdentifierRequestDTO request) {
    return projectMapper.toIdentifierDTO(getProjectByProjectNameAndUserEmail(request)); 
  }

  @Override
  public ProjectDTO updateProject(ProjectUpdateDTO updateDTO, String uuid) {
    Project project = getProjectByUuid(uuid);
    project.setProjectName(updateDTO.getProjectName());
    project.setProjectInformation(updateDTO.getProjectInformation());
    Project updatedProject = projectRepository.save(project);
    return projectMapper.toDTO(updatedProject);
  }


  @Override
  public void deleteProjectByUuid(String uuid) {
    if(!projectRepository.existsByUuid(uuid)) {
      throw new ProjectNotFoundException("Could not find project with uuid: " + uuid);
    }
    projectRepository.deleteByUuid(uuid);
  }

}
