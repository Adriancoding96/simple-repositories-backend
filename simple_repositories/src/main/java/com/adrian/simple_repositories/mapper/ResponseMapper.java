package com.adrian.simple_repositories.mapper;

import com.adrian.simple_repositories.model.File;
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.model.Repo;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.pull.PullResponseDTO;
import com.adrian.simple_repositories.dto.push.PushResponseDTO;
import com.adrian.simple_repositories.dto.repo.RepoFullDTO;

@Component
public class ResponseMapper {

  public PushResponseDTO toPushResponseFromFile(File file) {
    if(file == null) return new PushResponseDTO(false, "Failed to create file from push", null);
    return new PushResponseDTO(true, "File created successfully from push", file.getUuid());
  }

  public PushResponseDTO toPushResponseFromDirectory(Directory directory) {
    if(directory == null) return new PushResponseDTO(false, "Failed to create directory from push", null);
    return new PushResponseDTO(true, "Directory created successfully from push", directory.getUuid());
  }

  public PushResponseDTO toPushResponseFromRepo(Repo repo) {
    if(repo == null) return new PushResponseDTO(false, "Failed to create repo from push", null);
    return new PushResponseDTO(true, "Repository created successfully from push", repo.getUuid());
  }

  public PullResponseDTO toPullResponseFromRepo(RepoFullDTO dto) {
    if(dto == null) return new PullResponseDTO(false, "Failed to pull repository", null, null);
    return new PullResponseDTO(true, "Repository pulled successfully", dto.getUuid(), dto);
  }
}
