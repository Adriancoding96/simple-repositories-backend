package com.adrian.simple_repositories.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.directory.DirectoryFullDTO;
import com.adrian.simple_repositories.model.Directory;

@Component
public class DirectoryFullMapper {

  private final FileMapper fileMapper;

  @Autowired
  public DirectoryFullMapper(FileMapper fileMapper) {
    this.fileMapper = fileMapper;
  }

  public DirectoryFullDTO toFullDTO(Directory directory) {
    if(directory == null) return null;

    DirectoryFullDTO dto = new DirectoryFullDTO();
    dto.setDirectoryName(directory.getDirectoryName());

    if(directory.getFiles() != null || directory.getFiles().isEmpty()) {
      dto.setFiles(directory.getFiles().stream()
        .map(fileMapper::toDTO)
        .collect(Collectors.toList()));
    }

    if(directory.getDirectories() != null || directory.getDirectories().isEmpty()) {
      dto.setDirectories(directory.getDirectories().stream()
        .map(this::toFullDTO)
        .collect(Collectors.toList()));
    }

    return dto;
  }

  public Directory toEntity(DirectoryFullDTO dto) {
    if(dto == null) return null;

    Directory directory = new Directory();
    directory.setDirectoryName(dto.getDirectoryName());
  
    return directory;
  }
}
