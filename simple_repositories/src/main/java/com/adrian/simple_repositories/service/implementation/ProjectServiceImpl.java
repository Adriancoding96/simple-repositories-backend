package com.adrian.simple_repositories.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.assembler.ProjectAssembler;
import com.adrian.simple_repositories.dto.ProjectFullDTO;
import com.adrian.simple_repositories.dto.PushResponseDTO;
import com.adrian.simple_repositories.exception.ProjectNotFoundException;
import com.adrian.simple_repositories.mapper.ProjectMapper;
import com.adrian.simple_repositories.mapper.ResponseMapper;
import com.adrian.simple_repositories.model.Project;
import com.adrian.simple_repositories.repository.ProjectRepository;
import com.adrian.simple_repositories.service.ProjectService;

import jakarta.transaction.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectAssembler projectAssembler;
  private final ProjectMapper projectMapper;

  @Autowired
  public ProjectServiceImpl(ProjectRepository projectRepository, ProjectAssembler projectAssembler, ProjectMapper projectMapper) {
    this.projectRepository = projectRepository;
    this.projectAssembler = projectAssembler;
    this.projectMapper = projectMapper;
  }

  @Override
  @Transactional
  public Project createProjectFromPush(ProjectFullDTO projectDTO, String ownerEmail) {
    Project project = projectAssembler.assemble(projectDTO);
    Project savedProject = projectRepository.save(project);
    return savedProject;
  }

  @Override
  public Project getProjectById(Long projectId) {
    return projectRepository.findById(projectId)
      .orElseThrow(() -> new ProjectNotFoundException("Poject with id: " + projectId + " not found"));
    
  }
}
