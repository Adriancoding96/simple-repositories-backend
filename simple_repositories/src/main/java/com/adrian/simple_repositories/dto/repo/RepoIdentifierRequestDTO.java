package com.adrian.simple_repositories.dto.repo;

public class RepoIdentifierRequestDTO {

  private String repoName;

  private String email;

  public RepoIdentifierRequestDTO() {

  }
    
  public RepoIdentifierRequestDTO(String repoName, String email) {
    this.repoName = repoName;
    this.email = email;
  }

  public String getRepoName() {
    return repoName;
  }

  public String getEmail() {
    return email;
  }

  public void setRepoName(String repoName) {
    this.repoName = repoName;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
