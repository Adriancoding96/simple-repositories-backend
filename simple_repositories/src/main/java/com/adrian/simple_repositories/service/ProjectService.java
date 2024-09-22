package com.adrian.simple_repositories.service;

import java.util.List;

import com.adrian.simple_repositories.dto.ProjectDTO;
import com.adrian.simple_repositories.dto.ProjectFullDTO;

public interface ProjectService {
  ProjectFullDTO createFullProject(ProjectFullDTO projectFullDTO);
  ProjectDTO createProject(ProjectDTO projectDTO);
  List<ProjectDTO> getAllProjects();
  ProjectDTO getProjectById(Long id); 
  ProjectDTO updateProject(ProjectDTO projectDTO);
  void deleteProjectById(Long id);  
}
