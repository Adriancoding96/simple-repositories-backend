package com.adrian.simple_repositories.service;

import com.adrian.simple_repositories.dto.ProjectFullDTO;
import com.adrian.simple_repositories.dto.PushResponseDTO;
import com.adrian.simple_repositories.model.Project;

public interface ProjectService {

  Project createProjectFromPush(ProjectFullDTO projectDTO, String ownerEmail);

  Project getProjectById(Long projectId);
}
