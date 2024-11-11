package com.adrian.simple_repositories.dto.repo;

public class RepoUpdateDTO {

  String repoName;

  String repoInformation;

  public RepoUpdateDTO() {

  }

  public RepoUpdateDTO(String repoName, String repoInformation) {
    this.repoName = repoName;
    this.repoInformation = repoInformation;
  }

  public String getRepoName() {
    return repoName;
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
