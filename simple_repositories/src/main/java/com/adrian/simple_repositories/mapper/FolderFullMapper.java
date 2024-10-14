package com.adrian.simple_repositories.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.FolderFullDTO;
import com.adrian.simple_repositories.model.Folder;

@Component
public class FolderFullMapper {

  private final FileMapper fileMapper;

  @Autowired
  public FolderFullMapper(FileMapper fileMapper) {
    this.fileMapper = fileMapper;
  }

  public FolderFullDTO toFullDTO(Folder folder) {
    if(folder == null) return null;

    FolderFullDTO dto = new FolderFullDTO();
    dto.setId(folder.getId());
    dto.setFolderName(folder.getFolderName());

    if(folder.getFiles() != null || folder.getFiles().isEmpty()) {
      dto.setFiles(folder.getFiles().stream()
        .map(fileMapper::toDTO)
        .collect(Collectors.toList()));
    }

    if(folder.getFolders() != null || folder.getFolders().isEmpty()) {
      dto.setFolders(folder.getFolders().stream()
        .map(this::toFullDTO)
        .collect(Collectors.toList()));
    }

    return dto;
  }

  public Folder toEntity(FolderFullDTO dto) {
    if(dto == null) return null;

    Folder folder = new Folder();
    folder.setId(dto.getId());
    folder.setFolderName(dto.getFolderName());
  
    return folder;
  }
}
