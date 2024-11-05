package com.adrian.simple_repositories.mapper;

import com.adrian.simple_repositories.model.File;
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.model.Project;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.push.PushResponseDTO;

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

  public PushResponseDTO toPushResponseFromProject(Project project) {
    if(project == null) return new PushResponseDTO(false, "Failed to create project from push", null);
    return new PushResponseDTO(true, "Project created successfully from push", project.getUuid());
  }
}
