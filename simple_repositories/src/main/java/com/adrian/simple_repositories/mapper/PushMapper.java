package com.adrian.simple_repositories.mapper;

import com.adrian.simple_repositories.dto.push.PushDTO;
import com.adrian.simple_repositories.dto.push.PushRequestDTO;
import com.adrian.simple_repositories.dto.repo.RepoFullDTO;
import com.adrian.simple_repositories.dto.directory.DirectoryFullDTO;
import com.adrian.simple_repositories.dto.file.FileDTO;
import com.adrian.simple_repositories.model.Push;
import com.adrian.simple_repositories.exception.InvalidPushException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PushMapper {

  private final RepoMapper repoMapper;
  private final DirectoryMapper directoryMapper;
  private final FileMapper fileMapper;

  @Autowired
  public PushMapper(RepoMapper repoMapper, DirectoryMapper directoryMapper, FileMapper fileMapper) {
    this.repoMapper = repoMapper;
    this.directoryMapper = directoryMapper;
    this.fileMapper = fileMapper;
  }

  public PushDTO toDTO(Push push) {
    if(push == null) throw new InvalidPushException("Invalid push: Push is null");
    if(push.getRepo() == null && push.getDirectory() == null && push.getFile() == null) {
      throw new InvalidPushException("Invalid push: Does not contain repo/directory/file");
    }
    
    PushDTO dto = new PushDTO();
    dto.setId(push.getId());
    dto.setCommitHash(push.getCommitHash());
    dto.setCommitMessage(push.getCommitMessage());
    
    if(push.getRepo() != null) dto.setRepoDTO(setRepoBody(push));
    if(push.getDirectory() != null) dto.setDirectoryDTO(setDirectoryBody(push));
    if(push.getFile() != null) dto.setFileDTO(setFileBody(push));

    return dto;
  }

  private RepoFullDTO setRepoBody(Push push) {
    return repoMapper.toFullDTO(push.getRepo());
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
  public Push toEntity(PushRequestDTO request) {
    if(request.getContent() instanceof DirectoryFullDTO) {
      DirectoryFullDTO dto = (DirectoryFullDTO) request.getContent();
      return toEntityFromDirectoryRequest(dto, request);
    } else {
      FileDTO dto = (FileDTO) request.getContent();
      return toEntityFromFileRequest(dto, request);
    }
  }

  private Push toEntityFromDirectoryRequest(DirectoryFullDTO dto, PushRequestDTO request) {
    Push push = new Push();
    push.setCommitHash(request.getRepoUuid()); //TODO Currently the hash is the repo uuid, need to think about this some more
    push.setCommitMessage(request.getCommitMessage());
    //TODO How to handle directory mapping? if i persists push
    return push;
  }
  private Push toEntityFromFileRequest(FileDTO dto, PushRequestDTO request) {
    Push push = new Push();
    push.setCommitHash(request.getRepoUuid()); //TODO Currently the hash is the repo uuid, need to think about this some more
    push.setCommitMessage(request.getCommitMessage());
    //TODO How to handle file mapping?
    return push;
  }
}
