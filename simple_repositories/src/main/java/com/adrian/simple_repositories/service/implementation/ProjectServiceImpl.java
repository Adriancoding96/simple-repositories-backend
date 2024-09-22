package com.adrian.simple_repositories.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.dto.FileDTO;
import com.adrian.simple_repositories.dto.FolderFullDTO;
import com.adrian.simple_repositories.dto.ProjectDTO;
import com.adrian.simple_repositories.dto.ProjectFullDTO;
import com.adrian.simple_repositories.exception.FolderNotFoundException;
import com.adrian.simple_repositories.exception.ProjectNotFoundException;
import com.adrian.simple_repositories.mapper.FileMapper;
import com.adrian.simple_repositories.mapper.FolderMapper;
import com.adrian.simple_repositories.mapper.ProjectMapper;
import com.adrian.simple_repositories.model.File;
import com.adrian.simple_repositories.model.Folder;
import com.adrian.simple_repositories.model.Project;
import com.adrian.simple_repositories.repository.FileRepository;
import com.adrian.simple_repositories.repository.FolderRepository;
import com.adrian.simple_repositories.repository.ProjectRepository;
import com.adrian.simple_repositories.service.ProjectService;

import jakarta.transaction.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService {

  private ProjectRepository projectRepository;
  private ProjectMapper projectMapper;
  private FolderRepository folderRepository;
  private FileRepository fileRepository;
  private FolderMapper folderMapper;
  private FileMapper fileMapper;

  @Autowired
  public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper,
    FolderRepository folderRepository, FileRepository fileRepository, FolderMapper folderMapper, FileMapper fileMapper) {
    this.projectRepository = projectRepository;
    this.projectMapper = projectMapper;
    this.folderRepository = folderRepository;
    this.fileRepository = fileRepository;
    this.folderMapper = folderMapper;
    this.fileMapper = fileMapper;
  }

  @Transactional
  public ProjectFullDTO createFullProject(ProjectFullDTO projectFullDTO) {
    Project savedProject = createProjectFromFullDTO(projectFullDTO);
    for(FolderFullDTO folderFullDTO : projectFullDTO.getFolders()) {
      createFoldersFromFullDTO(folderFullDTO, savedProject, null);
    }
    return getFullProject(savedProject.getId()); 
  } 

  private Project createProjectFromFullDTO(ProjectFullDTO projectFullDTO) {
    return projectRepository.save(projectMapper.toEntityFromFullDTO(projectFullDTO));
  }

  private void createFoldersFromFullDTO(FolderFullDTO folderFullDTO, Project project, Folder parentFolder) {
    Folder folder = folderMapper.toEntityFromFullDTO(folderFullDTO);
    folder.setProject(project);

    if(parentFolder != null) {
      folder.setParentFolder(parentFolder);
    }

    System.out.println("Folder: " + folder.getFolderName() + ", Project: " + folder.getProject().getId() + ", ParentFolder: " + (folder.getParentFolder() != null ? folder.getParentFolder().getId() : "null"));
    Folder savedFolder = folderRepository.save(folder);
    System.out.println("Saved Folder ID: " + savedFolder.getId() + ", Project ID: " + savedFolder.getProject().getId());

    Folder newParentFolder = folder;
    if(!folderFullDTO.getFiles().isEmpty()) {
      createFilesFromFullFolderDTO(folderFullDTO, folder);
    }

    for(FolderFullDTO subFolder : folderFullDTO.getFolders()) {
      createFoldersFromFullDTO(subFolder, project, newParentFolder);
    }
  }

  private void createFilesFromFullFolderDTO(FolderFullDTO folderFullDTO, Folder folder) {
    for(FileDTO fileDTO : folderFullDTO.getFiles()) {
      File file = fileMapper.toEntity(fileDTO);
      file.setFolder(folder);
      fileRepository.save(file);
    }
  }

  public ProjectFullDTO getFullProject(Long projectId) {
    Project project = projectRepository.findById(projectId)
      .orElseThrow(() -> new ProjectNotFoundException("Could not find project with id: " + projectId));
    
    List<Folder> topLevelFolders = folderRepository.findAllFoldersByProjectId(projectId)
      .orElseThrow(() -> new FolderNotFoundException("Could not find folders with project id: " + projectId));

    for(Folder folder : topLevelFolders) {
      folder.setProject(project);
      loadFolderHierarchy(folder, project);
    }

    project.getFolders().clear();
    project.getFolders().addAll(topLevelFolders);
  
    System.out.println(project.toString());
    return projectMapper.toFullDTO(project);
  }

  private void loadFolderHierarchy(Folder folder, Project project) {
    List<Folder> subFolders = folderRepository.findAllSubFoldersByFolderIdExcludingSelf(folder.getId())
      .orElseThrow(() -> new FolderNotFoundException("Could not find folders with parent folder id: " + folder.getId()));
    folder.getFolders().clear();
    folder.getFolders().addAll(subFolders);
    for(Folder subFolder : subFolders) {
      subFolder.setProject(project);
    }

    List<File> files = fileRepository.findAllByFolderId(folder.getId())
      .orElseThrow(() -> new FolderNotFoundException("Could not files with folder id: " + folder.getId()));
    folder.getFiles().clear();
    folder.getFiles().addAll(files);

    for(Folder subFolder : subFolders) {
      loadFolderHierarchy(subFolder, project);
    }
  }

  public ProjectDTO createProject(ProjectDTO projectDTO) {
    try {
      Project savedProject = projectRepository.save(projectMapper.toEntity(projectDTO));          
      return projectMapper.toDTO(savedProject);
    } catch (Exception e) {
      throw new RuntimeException("Failed to save project to the database: " + e);
    }
  }

  public List<ProjectDTO> getAllProjects() {
    List<Project> projects = projectRepository.findAll();
    if(projects == null || projects.isEmpty()) {
      throw new ProjectNotFoundException("Could not fetch all projects");
    }
    return projects.stream()
      .map(projectMapper::toDTO)
      .collect(Collectors.toList());
  }

  public ProjectDTO getProjectById(Long id) { 
    Project project = projectRepository.findById(id)
      .orElseThrow(() -> new ProjectNotFoundException("Could not find project with id: " + id));
    return projectMapper.toDTO(project);

  }

  public ProjectDTO updateProject(ProjectDTO projectDTO) {
    Project project = projectRepository.findById(projectDTO.getId())
      .orElseThrow(() -> new ProjectNotFoundException("Could not fetch project with id: " + projectDTO.getId())); 
    Project updatedProject = updateProjectData(projectDTO, project);
    return projectMapper.toDTO(updatedProject);
  }

  public void deleteProjectById(Long id) {
    try {
      projectRepository.deleteById(id);
    } catch (Exception e) {
      throw new RuntimeException("Could not delete project with id: " + id);
    }
  }

  private Project updateProjectData(ProjectDTO projectDTO, Project project) {
    project.setId(projectDTO.getId());
    project.setProjectName(projectDTO.getProjectName());
    Optional<List<Folder>> optionalFolders = folderRepository.findAllFoldersByProjectId(projectDTO.getId());
    if(optionalFolders.isEmpty()) {
      throw new RuntimeException("Could not find folders with project id: " + projectDTO.getId());
    }
    project.setFolders(optionalFolders.get());
    return project;
  }
}
