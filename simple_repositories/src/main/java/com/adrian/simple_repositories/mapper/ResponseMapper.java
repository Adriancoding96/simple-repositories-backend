package com.adrian.simple_repositories.mapper;

import com.adrian.simple_repositories.model.File;
import com.adrian.simple_repositories.model.Folder;
import com.adrian.simple_repositories.model.Project;

import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.PushResponseDTO;

@Component
public class ResponseMapper {


  public PushResponseDTO toPushResponseFromFile(File file) {
    if(file == null) return new PushResponseDTO(false, "Failed to create file from push", null, null, null);
    return createPushResponse("file", file.getId(), "File created successfully from push");
  }

  public PushResponseDTO toPushResponseFromFolder(Folder folder) {
    if(folder == null) return new PushResponseDTO(false, "Failed to create folder from push", null, null, null);
    return createPushResponse("folder", folder.getId(), "Folder created successfully from push");
  }

  public PushResponseDTO toPushResponseFromProject(Project project) {
    if(project == null) return new PushResponseDTO(false, "Failed to create project from push", null, null, null);
    return createPushResponse("project", project.getId(), "Projecr created successfully from push");
  }

  private PushResponseDTO createPushResponse(String content, Long id, String message) {
    switch(content) {
      case "file": return new PushResponseDTO(true, message, null, null, id); 
      case "folder": return new PushResponseDTO(true, message, null, id, null);
      case "project": return new PushResponseDTO(true, message, id, null, null);
      default: {
        throw new RuntimeException("Could not create push response for: " + content + " with id: " + id);
      }
    }

  }
}
