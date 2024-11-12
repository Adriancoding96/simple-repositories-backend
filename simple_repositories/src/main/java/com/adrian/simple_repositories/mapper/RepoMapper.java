package com.adrian.simple_repositories.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.UniqueIdentifierDTO;
import com.adrian.simple_repositories.dto.repo.RepoDTO;
import com.adrian.simple_repositories.dto.repo.RepoFullDTO;
import com.adrian.simple_repositories.dto.repo.RepoInformationDTO;
import com.adrian.simple_repositories.dto.repo.RepoSetupDTO;
import com.adrian.simple_repositories.model.Repo;

@Component
public class RepoMapper {

  private final DirectoryMapper directoryMapper;

  @Autowired
  public RepoMapper(DirectoryMapper directoryMapper) {
    this.directoryMapper = directoryMapper;
  }

  public RepoFullDTO toFullDTO(Repo repo) {
    if(repo == null) return null; //TODO implement exception

    RepoFullDTO dto = new RepoFullDTO();
    dto.setUuid(repo.getUuid());
    dto.setRepoName(repo.getRepoName());
    dto.setRepoInformation(repo.getRepoInformation());
    dto.setDirectories(repo.getDirectories().stream()
      .map(directoryMapper::toFullDTO)
      .collect(Collectors.toList()));

    return dto;
  }

  public RepoDTO toDTO(Repo repo) {
    RepoDTO dto = new RepoDTO();
    dto.setRepoName(repo.getRepoName());
    dto.setRepoInformation(repo.getRepoInformation());
    return dto;
  } 


  public Repo toEntity(RepoDTO dto) {
    if(dto == null) return null;

    Repo repo = new Repo();
    repo.setRepoName(dto.getRepoName());
    repo.setRepoInformation(dto.getRepoInformation());
    
    return repo;
  }

  public Repo toEntityFromSetupDTO(RepoSetupDTO dto) {
    Repo repo = new Repo();

    repo.setRepoName(dto.getRepoName());
    repo.setRepoInformation(dto.getRepoInformation());
    return repo;
  }

  public RepoInformationDTO toInfoDTO(Repo repo) {
    RepoInformationDTO dto = new RepoInformationDTO();
    dto.setRepoName(repo.getRepoName());
    dto.setRepoInformation(repo.getRepoInformation());
    //File types set in service
    
    return dto;
  }

  public UniqueIdentifierDTO toIdentifierDTO(Repo repo) {
    return new UniqueIdentifierDTO("repo", repo.getUuid());
  }

}
