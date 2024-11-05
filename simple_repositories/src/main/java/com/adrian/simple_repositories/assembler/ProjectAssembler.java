package com.adrian.simple_repositories.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.directory.DirectoryFullDTO;
import com.adrian.simple_repositories.dto.project.ProjectFullDTO;
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.model.Project;
import com.adrian.simple_repositories.model.User;

@Component
public class ProjectAssembler {

  private final DirectoryAssembler directoryAssembler;

  @Autowired
  public ProjectAssembler(DirectoryAssembler directoryAssembler) {
    this.directoryAssembler = directoryAssembler;
  }

  public Project assemble(ProjectFullDTO dto, User user) {
    if(dto == null) return null;

    Project project = new Project();
    project.setProjectName(dto.getProjectName());
    project.setProjectInformation(dto.getProjectInformation());
    project.setUser(user);

    List<Directory> directories = new ArrayList<>();
    for(DirectoryFullDTO directoryDTO : dto.getDirectories()) {
      Directory directory = directoryAssembler.assemble(directoryDTO, project, null);
      directories.add(directory);
    }
    project.setDirectories(directories);
    
    return project;
  }
}
