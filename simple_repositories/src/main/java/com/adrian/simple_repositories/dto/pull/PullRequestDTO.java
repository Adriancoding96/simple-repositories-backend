package com.adrian.simple_repositories.dto.pull;

public class PullRequestDTO {
  
  private String repoUuid;
  
  public PullRequestDTO() {

  }

  public PullRequestDTO(String repoUuid) {
    this.repoUuid = repoUuid;
  }

  public String getRepoUuid() {
    return repoUuid;
  }

  public void setRepoUuid(String repoUuid) {
    this.repoUuid = repoUuid;
  }
}
