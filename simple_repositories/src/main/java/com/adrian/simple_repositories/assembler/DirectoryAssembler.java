package com.adrian.simple_repositories.assembler;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.file.FileDTO;
import com.adrian.simple_repositories.dto.directory.DirectoryFullDTO;
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.model.Repo;
import com.adrian.simple_repositories.model.File;

/*
 * DirectoryAssembler serves as the first stage in the assembly chain for building directory
 * entities and the second stage for repo entities to be persisted to the database.
 *
 * Injects FileAssembler to assemble nested file entities.
 */
@Component
public class DirectoryAssembler {

  private final FileAssembler fileAssembler;

  @Autowired
  public DirectoryAssembler(FileAssembler fileAssembler) {
    this.fileAssembler = fileAssembler;
  }

  /*
   * Assembles a directory entity from DirectoryFullDTO and associating it with Repo 
   * and ParentDirectory. It also calls the assemble method from FileAssembler which serves
   * as the next stage in the assembly chain. The method recursivly calls upon itself to
   * assemble subdirectories of the current directory.
   *
   * @param dto: the DTO containing directory data
   * @param repo: the repo which the directory belongs to
   * @return: the assembled directory entity 
   */
  public Directory assemble(DirectoryFullDTO dto, Repo repo, Directory parentDirectory) {
    if(dto == null || repo == null) return null; //TODO throw exception?
    
    Directory directory = new Directory();
    directory.setDirectoryName(dto.getDirectoryName());
    directory.setPath(dto.getPath());
    directory.setRepo(repo);
    if(parentDirectory != null) directory.setParentDirectory(parentDirectory);

    List<File> files = new ArrayList<>();
    for(FileDTO fileDTO : dto.getFiles()) {
      File file = fileAssembler.assemble(fileDTO);
      file.setDirectory(directory);
      files.add(file);
    }
    directory.setFiles(files);

    List<Directory> subDirectories = new ArrayList<>();
    for(DirectoryFullDTO subDirectoryDTO : dto.getDirectories()) {
      Directory subDirectory = assemble(subDirectoryDTO, repo, directory);
      subDirectories.add(subDirectory);
    }
    directory.setDirectories(subDirectories);

    return directory;
  }
}
