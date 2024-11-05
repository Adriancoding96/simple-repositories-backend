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

/*
 * Implementation of ProjectService interface
 *
 * Injects ProjectService to handle database operations on the projects table,
 * ProjectAssembler to construct project entity containing nested entities before persisting
 * to database, ProjectMapper to map projects to DTOs.
 */
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
  
  /*
   *  TODO Change method name to createProjectFromFullDTO ?
   *
   * Creates a new project from ProjectFullDTO and assiciating it with given user
   *
   * @param projectFullDTO: contains project data
   * @param user: contains user data of project owner
   * @return: persisted project entity
   */
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

  /*
   * Retrieves project by ID
   *
   * @param projectId: ID of project
   * @return project: returns project entity fetched from the database by ID
   * @throws ProjectNotFoundException: throws exception if project not found by ID
   */
  @Override
  public Project getProjectById(Long projectId) {
    return projectRepository.findById(projectId)
      .orElseThrow(() -> new ProjectNotFoundException("Poject with id: " + projectId + " not found"));
  }

  /*
   * Retrieves project by UUID
   *
   * @param uuid: UUID of project
   * @return project: returns project entity fetched from the database by ID
   * @throws ProjectNotFoundException: throws exception if project not found by uuid
   */
  @Override
  public Project getProjectByUuid(String uuid) {
    return projectRepository.findByUuid(uuid)
      .orElseThrow(() -> new ProjectNotFoundException("Could not find project with uuid: " + uuid));
  }

  /*
   * Retrieves project by UUID and converts it to ProjectFullDTO
   *
   * @param uuid: UUID of project
   * @return project: returns project entity fetched from database
   * @throws ProjectNotFoundException: throws exception if project not found by uuid
   */
  @Override
  public ProjectFullDTO getProjectAsDTOByUuid(String uuid) {
    return projectMapper.toFullDTO(getProjectByUuid(uuid));
  }

  /*
   * TODO split in to two methods, one for fetching all projects and one for converting projects to information DTOs
   *
   * Retrieves all projects and converts them to information DTOs
   *
   * @return pushInfoDTOs: returns list of DTOs containing push information
   */
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

  /*
   * Converts a list of projects to project information DTOs
   *
   * @param projects: list of projects
   * @return projectInfoDTOs: returns list of project information DTOs
   */
  private List<ProjectInformationDTO> converToProjectInfoDTOs(List<Project> projects) {
    return projects.stream()
      .map(projectMapper::toInfoDTO)
      .collect(Collectors.toList());
  }

  /*
   * Initializing file system traversal for extracting extensions of files nested in project
   *
   * @param project: project entity
   * @returns extension: list of strings containing file extensions
   */
  private List<String> getAllFileExtionsFromProject(Project project) {
    List<String> extensions = new ArrayList<>();
    for(Directory directory : project.getDirectories()) {
      walkThroughDirectoryTree(directory, extensions);
    }

    return extensions;
  }

  /*
   *  Recursivly walk through directory tree, calling helper method to extract file extensions of current directory
   *
   *  @param directory: directory of current recursive itteration
   *  @param extension: list of strings containing extensions from passed iterations (will be empty during first iteration)
   *  @return void: no need to return anything since we are updating content of a ArrayList stored on the heap
   */
  private void walkThroughDirectoryTree(Directory directory, List<String> extensions) {
    if(directory.getFiles() != null || !directory.getFiles().isEmpty()) {
      getFileExtensionsFromDirectory(directory, extensions);
    } 

    if(directory.getDirectories() == null || directory.getDirectories().isEmpty()) return;
    for(Directory subDirectory : directory.getDirectories()) {
      walkThroughDirectoryTree(subDirectory, extensions);
    }
  } 

  /*
   * Helper method to extract file extension from directory and store it in a list
   *
   * @param directory: directory containng files
   * @param extension: list of strings containing extensions from passed iterations from calling method (will be empty during first iteration)
   * @return void: no need to return anything since we are updating content of a ArrayList stored on the heap
   */
  private void getFileExtensionsFromDirectory(Directory directory, List<String> extensions) {
    for(File file : directory.getFiles()) {
      extensions.add(file.getExtension());
    }
  }

  /*
   * Retrieves project from database by project identifier request DTO containg project uuid and user details
   *
   * @param request: DTO containing project uuid and user details
   * @return projects: list of projects fetched from database by uuid and user details
   * @throws ProjectNotFoundException: throws exception if project was not found by uuid and user details
   */
  @Override
  public Project getProjectByProjectNameAndUserEmail(ProjectIdentifierRequestDTO request) {
    return projectRepository.findProjectByProjectNameAndUserEmail(request.getProjectName(), request.getEmail())
      .orElseThrow(() -> new ProjectNotFoundException("Could not fetch project created by user: " + request.getEmail() + " with project name: " + request.getProjectName()));
  }

  /*
   * Retrieves project uuid from database by project identifier request DTO and converts it to a unique identifier DTO
   * 
   * @param request: DTO cotaining project uuid and user details
   * @return uniqueIdentifierDTO: DTO containing project uuid
   */
  @Override
  public UniqueIdentifierDTO getProjectIdentiferByProjectNameAndUserEmail(ProjectIdentifierRequestDTO request) {
    return projectMapper.toIdentifierDTO(getProjectByProjectNameAndUserEmail(request)); 
  }

  /*
   * Retrieves project from database by uuid, updates project with data from project update DTO
   *
   * @param updateDTO: contains data to be updated in project
   * @param uuid: contains uuid of project to be updated
   */
  @Override
  public ProjectDTO updateProject(ProjectUpdateDTO updateDTO, String uuid) {
    Project project = getProjectByUuid(uuid);
    project.setProjectName(updateDTO.getProjectName());
    project.setProjectInformation(updateDTO.getProjectInformation());
    Project updatedProject = projectRepository.save(project);
    return projectMapper.toDTO(updatedProject);
  }

  /*
   * Deletes project by uuid
   * @param uuid: contains uuid of project to be deleted
   * @throws ProjectNotFoundException: throws exception if project with uuid was not found
   */
  @Override
  public void deleteProjectByUuid(String uuid) {
    if(!projectRepository.existsByUuid(uuid)) {
      throw new ProjectNotFoundException("Could not find project with uuid: " + uuid);
    }
    projectRepository.deleteByUuid(uuid);
  }

}
