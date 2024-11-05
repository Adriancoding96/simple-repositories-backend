package com.adrian.simple_repositories.assembler;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.file.FileDTO;
import com.adrian.simple_repositories.dto.directory.DirectoryFullDTO;
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.model.Project;
import com.adrian.simple_repositories.model.File;

@Component
public class DirectoryAssembler {

  private final FileAssembler fileAssembler;

  @Autowired
  public DirectoryAssembler(FileAssembler fileAssembler) {
    this.fileAssembler = fileAssembler;
  }

  public Directory assemble(DirectoryFullDTO dto, Project project, Directory parentDirectory) {
    if(dto == null || project == null) return null; 
    
    Directory directory = new Directory();
    directory.setDirectoryName(dto.getDirectoryName());
    directory.setPath(dto.getPath());
    directory.setProject(project);
    directory.setParentDirectory(parentDirectory);

    List<File> files = new ArrayList<>();
    for(FileDTO fileDTO : dto.getFiles()) {
      File file = fileAssembler.assemble(fileDTO);
      file.setDirectory(directory);
      files.add(file);
    }
    directory.setFiles(files);

    List<Directory> subDirectories = new ArrayList<>();
    for(DirectoryFullDTO subDirectoryDTO : dto.getDirectories()) {
      Directory subDirectory = assemble(subDirectoryDTO, project, directory);
      subDirectories.add(subDirectory);
    }
    directory.setDirectories(subDirectories);

    return directory;
  }
}
