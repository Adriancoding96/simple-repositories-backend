package com.adrian.simple_repositories.dto.repo;
 
import java.util.List;

public class RepoSetupDTO {
  
  private String repoName;
  private String repoInformation;
  private String ownerEmail;

  public RepoSetupDTO() {

  }

  public RepoSetupDTO(String repoName, String repoInformation, String ownerEmail) {
    this.repoName = repoName;
    this.repoInformation = repoInformation;
    this.ownerEmail = ownerEmail;
  }

  public String getRepoName() {
    return this.repoName;
  }

  public String getRepoInformation() {
    return repoInformation;
  }

  public String getOwnerEmail() {
    return ownerEmail;
  }

  public void setRepoName(String repoName) {
    this.repoName = repoName;
  }

  public void setRepoInformation(String repoInformation) {
    this.repoInformation = repoInformation;
  }

  public void setOwnerEmail(String ownerEmail) {
    this.ownerEmail = ownerEmail;
  }
}
