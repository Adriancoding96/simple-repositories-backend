package com.adrian.simple_repositories.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.directory.DirectoryFullDTO;
import com.adrian.simple_repositories.dto.repo.RepoFullDTO;
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.model.Repo;
import com.adrian.simple_repositories.model.User;

/*
 * Repo Assembler serves as the beginning of a assembler chain
 * for creating repo enitities ready to be persisted to the database.
 *
 * Injects DirectoryAssembler to handle nested directory data.
 */
@Component
public class RepoAssembler {

  private final DirectoryAssembler directoryAssembler;

  @Autowired
  public RepoAssembler(DirectoryAssembler directoryAssembler) {
    this.directoryAssembler = directoryAssembler;
  }

  /*
   * Creates repo with nested directory and file entites by calling assemble method
   * from directory assembler which is the second class in the assembly chain
   *
   * @param dto: contains repo data
   * @param user: contains user data of repo owner
   * @return: assembeled repo entity
   */
  public Repo assemble(RepoFullDTO dto, User user) {
    if(dto == null) return null;

    Repo repo = new Repo();
    repo.setRepoName(dto.getRepoName());
    repo.setRepoInformation(dto.getRepoInformation());
    repo.setUser(user);

    List<Directory> directories = new ArrayList<>();
    for(DirectoryFullDTO directoryDTO : dto.getDirectories()) {
      Directory directory = directoryAssembler.assemble(directoryDTO, repo, null);
      directories.add(directory);
    }
    repo.setDirectories(directories);
    
    return repo;
  }
}
