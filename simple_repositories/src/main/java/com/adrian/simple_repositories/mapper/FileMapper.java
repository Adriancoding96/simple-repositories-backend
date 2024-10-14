package com.adrian.simple_repositories.mapper;

import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.FileDTO;
import com.adrian.simple_repositories.model.File;

@Component
public class FileMapper {

  public FileDTO toDTO(File file) {
    if(file == null) return null;

    FileDTO dto = new FileDTO();
    dto.setId(file.getId());
    dto.setFileName(file.getFileName());
    dto.setExtension(file.getExtension());
    dto.setContent(file.getContent());
    dto.setFolderId(file.getFolder() != null ? file.getFolder().getId() : null);
    return dto;
  }

  public File toEntity(FileDTO dto) {
    if(dto == null) return null;

    File file = new File();
    file.setId(dto.getId());
    file.setFileName(dto.getFileName());
    file.setExtension(dto.getExtension());
    file.setContent(dto.getContent());
    
    return file;
  }
}
