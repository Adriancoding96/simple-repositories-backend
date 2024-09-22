package com.adrian.simple_repositories.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.service.FolderService;
import com.adrian.simple_repositories.dto.FolderDTO;
import com.adrian.simple_repositories.exception.FolderNotFoundException;
import com.adrian.simple_repositories.exception.ProjectNotFoundException;
import com.adrian.simple_repositories.mapper.FolderMapper;
import com.adrian.simple_repositories.model.Folder;
import com.adrian.simple_repositories.model.Project;
import com.adrian.simple_repositories.repository.FolderRepository;
import com.adrian.simple_repositories.repository.ProjectRepository;

@Service
public class FolderServiceImpl implements FolderService {

  private FolderMapper folderMapper;
  private FolderRepository folderRepository;
  private ProjectRepository projectRepository;

  @Autowired
  public FolderServiceImpl (FolderMapper folderMapper, FolderRepository folderRepository, ProjectRepository projectRepository) {
    this.folderMapper = folderMapper;
    this.folderRepository = folderRepository;
    this.projectRepository = projectRepository;
  }


  public FolderDTO createFolder(FolderDTO folderDTO, Long projectId) {
    Folder folder = folderMapper.toEntity(folderDTO);
    Project project = projectRepository.findById(projectId)
      .orElseThrow(() -> new ProjectNotFoundException("Could not find project with id: " + projectId));
    
    folder.setProject(project);
    try {
      Folder savedFolder = folderRepository.save(folder);
      return folderMapper.toDTO(savedFolder);
    } catch (Exception e) {
      throw new RuntimeException("Could not save folder: " + folder);
    }
  }

  public List<FolderDTO> createFolders(List<FolderDTO> folderDTOs, Long projectId) {
    Project project = projectRepository.findById(projectId)
      .orElseThrow(() -> new ProjectNotFoundException("Could not find project with id: " + projectId));

    Folder folder;
    List<Folder> folders = new ArrayList<>();
    for(FolderDTO folderDTO : folderDTOs) {
      folder = folderMapper.toEntity(folderDTO);
      folder.setProject(project);
      folders.add(folder);
    }
    
    try {
      List<Folder> savedFolders = folderRepository.saveAll(folders);
      return savedFolders.stream()
        .map(folderMapper::toDTO)
        .collect(Collectors.toList());
    } catch (Exception e) {
      throw new RuntimeException("Could not save collection of folders: " + folders);
    }
  }

  public List<FolderDTO> getAllFolders() {
    List<Folder> folders = folderRepository.findAll();
    if(folders.isEmpty()) {
      throw new FolderNotFoundException("Could not fetch folders from database");
    }
    return folders.stream()
      .map(folderMapper::toDTO)
      .collect(Collectors.toList());
  }

  public List<FolderDTO> getAllFoldersByProject(Long projectId) {
    List<Folder> folders = folderRepository.findAllFoldersByProjectId(projectId) //TODO Change query to not return optional
      .orElseThrow(() -> new FolderNotFoundException("Could not find folders with project id: " + projectId));
    if(folders.isEmpty()) {
      throw new FolderNotFoundException("Could not find folders with project id: " + projectId);
    }
    return folders.stream()
      .map(folderMapper::toDTO)
      .collect(Collectors.toList());
  }

  public List<FolderDTO> getAllSubFolders(Long parentFolderId) {
    List<Folder> subFolders = folderRepository.findNestedFoldersByParentFolderId(parentFolderId);
    if(subFolders.isEmpty()) {
      throw new FolderNotFoundException("Could not find sub folders of parent folder with id: " + parentFolderId);
    }
    return subFolders.stream()
      .map(folderMapper::toDTO)
      .collect(Collectors.toList());
  }
  
  public FolderDTO getFolderById(Long folderId) {
    Folder folder = folderRepository.findById(folderId)
      .orElseThrow(() -> new FolderNotFoundException("Could not find folder with id: " + folderId));
    return folderMapper.toDTO(folder);
  }

  public FolderDTO updateFolder(FolderDTO folderDTO) {
    Folder folder = new Folder();
    folder.setFolderName(folderDTO.getFolderName());
    //TODO Implement logic for updating file and folder relationships to folder
    return folderMapper.toDTO(folder);
  }

  public void deleteFolderById(Long id) {
    try {
      folderRepository.deleteById(id);  
    } catch (Exception e) {
      throw new RuntimeException("Could not delete folder with id: " + id);
    } 
  } 
}
