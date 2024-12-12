package com.adrian.simple_repositories.dto.repo;
 

public class RepoDTO {
  
  private String repoName;
  private String repoInformation;

  public RepoDTO() {

  }

  public RepoDTO(String repoName, String repoInformation) {
    this.repoName = repoName;
    this.repoInformation = repoInformation;
  }

  public String getRepoName() {
    return this.repoName;
  }

  public String getRepoInformation() {
    return repoInformation;
  }

  public void setRepoName(String repoName) {
    this.repoName = repoName;
  }

  public void setRepoInformation(String repoInformation) {
    this.repoInformation = repoInformation;
  }
}
