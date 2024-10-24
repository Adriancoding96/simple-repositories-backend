package com.adrian.simple_repositories.mapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import com.adrian.simple_repositories.dto.folder.FolderDTO;
import com.adrian.simple_repositories.dto.folder.FolderFullDTO;
import com.adrian.simple_repositories.model.Folder;
import com.adrian.simple_repositories.model.File;

@Component
public class FolderMapper {

 
  private final FileMapper fileMapper;

  public FolderMapper(FileMapper fileMapper) {
    this.fileMapper = fileMapper;
  }

  public FolderDTO toDTO(Folder folder) {
    if(folder == null) return null;

    FolderDTO dto = new FolderDTO();
    dto.setFolderUuid(folder.getUuid());
    dto.setFolderName(folder.getFolderName());
    dto.setParentFolderUuid(folder.getParentFolder() != null ? folder.getParentFolder().getUuid() : null);

    return dto;
  }

  public FolderFullDTO toFullDTO(Folder folder) {
    if(folder == null) return null;

    FolderFullDTO dto = new FolderFullDTO();
    dto.setFolderName(folder.getFolderName());
    dto.setPath(folder.getPath());
    dto.setProjectId(folder.getProject() != null ? folder.getProject().getId() : null);
    dto.setParentFolderId(folder.getParentFolder() != null ? folder.getParentFolder().getId() : null);
    
    if(folder.getFiles() != null) {
      List<File> files = folder.getFiles();
      dto.setFiles(files.stream()
        .map(fileMapper::toDTO)
        .collect(Collectors.toList()));
    }

    if(folder.getFolders() != null) {
      List<Folder> subFolders = folder.getFolders();
      dto.setFolders(subFolders.stream()
        .map(this::toFullDTO)
        .collect(Collectors.toList()));
    }

    return dto;
  }




  public Folder toEntity(FolderDTO dto) {
    if(dto == null) return null;

    Folder folder = new Folder();
    folder.setId(dto.getId());
    folder.setFolderName(dto.getFolderName());

    return folder;
  }
}
