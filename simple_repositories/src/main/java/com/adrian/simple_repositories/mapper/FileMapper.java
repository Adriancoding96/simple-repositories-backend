package com.adrian.simple_repositories.mapper;

import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.file.FileDTO;
import com.adrian.simple_repositories.model.File;

@Component
public class FileMapper {

  public FileDTO toDTO(File file) {
    if(file == null) return null;

    FileDTO dto = new FileDTO();
    dto.setFileName(file.getFileName());
    dto.setExtension(file.getExtension());
    dto.setPath(file.getPath());
    dto.setContent(file.getContent());
    dto.setDirectoryId(file.getDirectory() != null ? file.getDirectory().getId() : null);
    dto.setUuid(file.getUuid());
    return dto;
  }

  public File toEntity(FileDTO dto) {
    if(dto == null) return null;

    File file = new File();
    file.setFileName(dto.getFileName());
    file.setExtension(dto.getExtension());
    file.setPath(dto.getPath());
    file.setContent(dto.getContent());
    file.setUuid(dto.getUuid());

    return file;
  }
}
