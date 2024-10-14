package com.adrian.simple_repositories.mapper;

import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.FolderDTO;
import com.adrian.simple_repositories.model.Folder;

@Component
public class FolderMapper {

  public FolderDTO toDTO(Folder folder) {
    if(folder == null) return null;

    FolderDTO dto = new FolderDTO();
    dto.setId(folder.getId());
    dto.setFolderName(folder.getFolderName());
    dto.setProjectId(folder.getProject() != null ? folder.getProject().getId() : null);
    dto.setParentFolderId(folder.getParentFolder() != null ? folder.getParentFolder().getId() : null);
    
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
