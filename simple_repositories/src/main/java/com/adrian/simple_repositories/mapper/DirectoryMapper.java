package com.adrian.simple_repositories.mapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import com.adrian.simple_repositories.dto.directory.DirectoryDTO;
import com.adrian.simple_repositories.dto.directory.DirectoryFullDTO;
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.model.File;

@Component
public class DirectoryMapper {

 
  private final FileMapper fileMapper;

  public DirectoryMapper(FileMapper fileMapper) {
    this.fileMapper = fileMapper;
  }

  public DirectoryDTO toDTO(Directory directory) {
    if(directory == null) return null;

    DirectoryDTO dto = new DirectoryDTO();
    dto.setDirectoryUuid(directory.getUuid());
    dto.setDirectoryName(directory.getDirectoryName());
    dto.setParentDirectoryUuid(directory.getParentDirectory() != null ? directory.getParentDirectory().getUuid() : null);

    return dto;
  }

  public DirectoryFullDTO toFullDTO(Directory directory) {
    if(directory == null) return null;

    DirectoryFullDTO dto = new DirectoryFullDTO();
    dto.setDirectoryName(directory.getDirectoryName());
    dto.setPath(directory.getPath());
    dto.setProjectId(directory.getProject() != null ? directory.getProject().getId() : null);
    dto.setParentDirectoryId(directory.getParentDirectory() != null ? directory.getParentDirectory().getId() : null);
    
    if(directory.getFiles() != null) {
      List<File> files = directory.getFiles();
      dto.setFiles(files.stream()
        .map(fileMapper::toDTO)
        .collect(Collectors.toList()));
    }

    if(directory.getDirectories() != null) {
      List<Directory> subDirectories = directory.getDirectories();
      dto.setDirectories(subDirectories.stream()
        .map(this::toFullDTO)
        .collect(Collectors.toList()));
    }

    return dto;
  }




  public Directory toEntity(DirectoryDTO dto) {
    if(dto == null) return null;

    Directory directory = new Directory();
    directory.setId(dto.getId());
    directory.setDirectoryName(dto.getDirectoryName());

    return directory;
  }
}
