package com.adrian.simple_repositories.mapper;

import com.adrian.simple_repositories.model.File;
import com.adrian.simple_repositories.model.Folder;
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

  public PushResponseDTO toPushResponseFromFolder(Folder folder) {
    if(folder == null) return new PushResponseDTO(false, "Failed to create folder from push", null);
    return new PushResponseDTO(true, "Folder created successfully from push", folder.getUuid());
  }

  public PushResponseDTO toPushResponseFromProject(Project project) {
    if(project == null) return new PushResponseDTO(false, "Failed to create project from push", null);
    return new PushResponseDTO(true, "Project created successfully from push", project.getUuid());
  }
}
