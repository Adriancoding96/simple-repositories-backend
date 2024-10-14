package com.adrian.simple_repositories.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.FolderFullDTO;
import com.adrian.simple_repositories.dto.ProjectFullDTO;
import com.adrian.simple_repositories.model.Folder;
import com.adrian.simple_repositories.model.Project;

@Component
public class ProjectAssembler {

  private final FolderAssembler folderAssembler;

  @Autowired
  public ProjectAssembler(FolderAssembler folderAssembler) {
    this.folderAssembler = folderAssembler;
  }

  public Project assemble(ProjectFullDTO dto) {
    if(dto == null) return null;

    Project project = new Project();
    project.setProjectName(dto.getProjectName());
    project.setProjectInformation(dto.getProjectInformation());

    List<Folder> folders = new ArrayList<>();
    for(FolderFullDTO folderDTO : dto.getFolders()) {
      Folder folder = folderAssembler.assemble(folderDTO, project, null);
      folders.add(folder);
    }
    project.setFolders(folders);
    
    return project;
  }
}
