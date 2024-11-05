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

/*
 * Project Assembler serves as the beginning of a assembler chain
 * for creating project enitities ready to be persisted to the database.
 *
 * Injects DirectoryAssembler to handle nested directory data.
 */
@Component
public class ProjectAssembler {

  private final DirectoryAssembler directoryAssembler;

  @Autowired
  public ProjectAssembler(DirectoryAssembler directoryAssembler) {
    this.directoryAssembler = directoryAssembler;
  }

  /*
   * Creates project with nested directory and file entites by calling assemble method
   * from directory assembler which is the second class in the assembly chain
   *
   * @param dto: contains project data
   * @param user: contains user data of project owner
   * @return: assembeled project entity
   */
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
