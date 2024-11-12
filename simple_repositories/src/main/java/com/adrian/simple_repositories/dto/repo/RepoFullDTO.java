package com.adrian.simple_repositories.dto.repo;

import java.util.List;

import com.adrian.simple_repositories.dto.directory.DirectoryFullDTO;

public class RepoFullDTO {

  private String repoName;
  private String repoInformation;
  private List<DirectoryFullDTO> directories;

  public RepoFullDTO() {

  }

  public RepoFullDTO(String repoName, String repoInformation, List<DirectoryFullDTO> directories) {
    this.repoName = repoName;
    this.repoInformation = repoInformation;
    this.directories = directories;
  }

  public String getRepoName() {
    return repoName;
  }

  public String getRepoInformation() {
    return repoInformation;
  }

  public List<DirectoryFullDTO> getDirectories() {
    return directories;
  }

  public void setRepoName(String repoName) {
    this.repoName = repoName;
  }

  public void setRepoInformation(String repoInformation) {
    this.repoInformation = repoInformation;
  }

  public void setDirectories(List<DirectoryFullDTO> directories) {
    this.directories = directories;
  }

}