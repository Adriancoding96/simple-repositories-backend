package com.adrian.simple_repositories.mapper;

import com.adrian.simple_repositories.dto.push.PushDTO;
import com.adrian.simple_repositories.dto.project.ProjectFullDTO;
import com.adrian.simple_repositories.dto.directory.DirectoryFullDTO;
import com.adrian.simple_repositories.dto.file.FileDTO;
import com.adrian.simple_repositories.model.Push;
import com.adrian.simple_repositories.exception.InvalidPushException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PushMapper {

  private final ProjectMapper projectMapper;
  private final DirectoryMapper directoryMapper;
  private final FileMapper fileMapper;

  @Autowired
  public PushMapper(ProjectMapper projectMapper, DirectoryMapper directoryMapper, FileMapper fileMapper) {
    this.projectMapper = projectMapper;
    this.directoryMapper = directoryMapper;
    this.fileMapper = fileMapper;
  }

  public PushDTO toDTO(Push push) {
    if(push == null) throw new InvalidPushException("Invalid push: Push is null");
    if(push.getProject() == null && push.getDirectory() == null && push.getFile() == null) {
      throw new InvalidPushException("Invalid push: Does not contain project/directory/file");
    }
    
    PushDTO dto = new PushDTO();
    dto.setId(push.getId());
    dto.setCommitHash(push.getCommitHash());
    dto.setCommitMessage(push.getCommitMessage());
    
    if(push.getProject() != null) dto.setProjectDTO(setProjectBody(push));
    if(push.getDirectory() != null) dto.setDirectoryDTO(setDirectoryBody(push));
    if(push.getFile() != null) dto.setFileDTO(setFileBody(push));

    return dto;
  }

  private ProjectFullDTO setProjectBody(Push push) {
    return projectMapper.toFullDTO(push.getProject());
  }

  private DirectoryFullDTO setDirectoryBody(Push push) {
    return directoryMapper.toFullDTO(push.getDirectory()); 
  }

  private FileDTO setFileBody(Push push) {
    return fileMapper.toDTO(push.getFile());
  }

  public Push toEntity(PushDTO dto) {
    if(dto == null) return null;

    Push push = new Push();
    push.setId(dto.getId());
    push.setCommitHash(dto.getCommitHash());
    push.setCommitMessage(dto.getCommitMessage());
    
    return push;
  }

}
