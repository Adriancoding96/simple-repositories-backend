package com.adrian.simple_repositories.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.dto.FolderDTO;
import com.adrian.simple_repositories.dto.FolderFullDTO;
import com.adrian.simple_repositories.exception.FolderNotFoundException;
import com.adrian.simple_repositories.exception.ProjectNotFoundException;
import com.adrian.simple_repositories.model.File;
import com.adrian.simple_repositories.model.Folder;
import com.adrian.simple_repositories.model.Project;
import com.adrian.simple_repositories.repository.FileRepository;
import com.adrian.simple_repositories.repository.FolderRepository;
import com.adrian.simple_repositories.repository.ProjectRepository;
import com.adrian.simple_repositories.util.ValidationUtils;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
public class FolderMapper {


  private FolderRepository folderRepository;
  private FileRepository fileRepository;
  private ProjectRepository projectRepository;
  private FileMapper fileMapper;

  @Autowired
  public FolderMapper(FolderRepository folderRepository, FileRepository fileRepository,
      ProjectRepository projectRepository, FileMapper fileMapper) {
    this.folderRepository = folderRepository;
    this.fileRepository = fileRepository;
    this.projectRepository = projectRepository;
    this.fileMapper = fileMapper;
    
  }

  public FolderDTO toDTO(Folder folder) {
    ValidationUtils.validateObject(folder);
    
    FolderDTO folderDTO = new FolderDTO();
    folderDTO.setId(folder.getId());
    folderDTO.setFolderName(folder.getFolderName());
    folderDTO.setFiles(getFileIds(folder.getFiles()));
    folderDTO.setFolders(getFolderIds(folder.getFolders()));
    folderDTO.setProject(folder.getProject().getId());
    folderDTO.setParentFolder(folder.getParentFolder().getId());

    return folderDTO;

  } 

  public Folder toEntity(FolderDTO folderDTO) {
    ValidationUtils.validateObject(folderDTO);
    
    Folder folder = new Folder();
    folder.setId(folderDTO.getId());
    folder.setFolderName(folderDTO.getFolderName());
    folder.setFiles(getAllFiles(folderDTO.getId()));
    folder.setFolders(getAllFolders(folderDTO.getId()));
    folder.setProject(getProject(folderDTO.getProject()));
    folder.setParentFolder(getParentFolder(folderDTO.getParentFolder()));

    return folder;
  }

  public FolderFullDTO toFullDTO(Folder folder) {
    ValidationUtils.validateObject(folder);

    FolderFullDTO folderFullDTO = new FolderFullDTO();
    folderFullDTO.setId(folder.getId());
    folderFullDTO.setFolderName(folder.getFolderName());
    
    if(folder.getFiles() != null) {
      List<File> files = folder.getFiles();
      folderFullDTO.setFiles(files.stream()
        .map(fileMapper::toDTO)
        .collect(Collectors.toList()));
    }

    if(folder.getFolders() != null) {
      List<Folder> subFolders = folder.getFolders();
      folderFullDTO.setFolders(subFolders.stream()
        .map(this::toFullDTO)
        .collect(Collectors.toList()));
    }
    folderFullDTO.setProjectId(folder.getProject().getId());
   
    return folderFullDTO;
  }

  public Folder toEntityFromFullDTO(FolderFullDTO folderFullDTO) {
    ValidationUtils.validateObject(folderFullDTO);
    Folder folder = new Folder();
    folder.setId(folderFullDTO.getId());
    folder.setFolderName(folderFullDTO.getFolderName());
    /*
    folder.setFiles(folderFullDTO.getFiles()
      .stream()
      .map(fileMapper::toEntity)
      .collect(Collectors.toList()));
    
    folder.setFolders(folderFullDTO.getFolders()
      .stream()
      .map(this::toEntityFromFullDTO)
      .collect(Collectors.toList()));
*/
//    folder.setProject(getProject(folderFullDTO.getProjectId()));
    return folder;
  }

  private List<Long> getFileIds(List<File> files) {
    List<Long> fileIds = new ArrayList<>();
    for(File file : files) {
      fileIds.add(file.getId());
    }
    return fileIds;
  }

  private List<Long> getFolderIds(List<Folder> folders) {
    List<Long> folderIds = new ArrayList<>();
    for(Folder folder : folders) {
      folderIds.add(folder.getId());
    }
    return folderIds;
  }

  private List<File> getAllFiles(Long folderId) {
    List<File> files = fileRepository.findAllByFolderId(folderId) //TODO Implement custom exceptions
      .orElseThrow(() -> new RuntimeException("Could not find files that belong to folder with id: " + folderId));
    return files;
  }

  private List<Folder> getAllFolders(Long folderId) {
    List<Folder> folders = folderRepository.findAllSubFoldersByFolderIdExcludingSelf(folderId) //TODO Implement custom exceptions
      .orElseThrow(() -> new RuntimeException("Could not find sub folders of folder with id: " + folderId));
    return folders; 
  }

  private Project getProject(Long projectId) {
    Project project = projectRepository.findById(projectId) //TODO implement custom exceptions
      .orElseThrow(() -> new ProjectNotFoundException("Could not find project with id: " + projectId));
    return project;
  }

  private Folder getParentFolder(Long parentFolderId) {
    Folder folder = folderRepository.findById(parentFolderId) //TODO Implement custom exceptions
      .orElseThrow(() -> new FolderNotFoundException("Could not find folder with id: " + parentFolderId));
    return folder;
  }
}
