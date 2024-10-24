package com.adrian.simple_repositories.service;

import java.util.List;

import com.adrian.simple_repositories.dto.project.ProjectDTO;
import com.adrian.simple_repositories.dto.project.ProjectFullDTO;
import com.adrian.simple_repositories.dto.project.ProjectInformationDTO;
import com.adrian.simple_repositories.dto.project.ProjectUpdateDTO;
import com.adrian.simple_repositories.dto.project.ProjectIdentifierRequestDTO;
import com.adrian.simple_repositories.dto.UniqueIdentifierDTO;
import com.adrian.simple_repositories.model.Project;
import com.adrian.simple_repositories.model.User;

public interface ProjectService {

  Project createProjectFromPush(ProjectFullDTO projectDTO, User user);

  Project getProjectById(Long projectId);
 
  Project getProjectByUuid(String uuid);

  ProjectFullDTO getProjectAsDTOByUuid(String uuid);

  List<ProjectInformationDTO> getAllProjectsAsInfoDTOs();

  Project getProjectByProjectNameAndUserEmail(ProjectIdentifierRequestDTO request);

  UniqueIdentifierDTO getProjectIdentiferByProjectNameAndUserEmail(ProjectIdentifierRequestDTO request);

  ProjectDTO updateProject(ProjectUpdateDTO updateDTO, String uuid);

  void deleteProjectByUuid(String uuid);
}
