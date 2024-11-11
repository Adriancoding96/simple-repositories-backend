package com.adrian.simple_repositories.dto.branch;

import java.util.UUID;

public class BranchRequestDTO {
  
  private String branchName;
  
  private UUID repoId;

  public BranchRequestDTO() {

  }

  public BranchRequestDTO(String branchName, UUID repoId) {
    this.branchName = branchName;
    this.repoId = repoId;
  }

  public String getBranchName() {
    return branchName;
  }

  public UUID getRepoId() {
    return repoId;
  }

  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }

  public void setRepoId(UUID repoId) {
    this.repoId = repoId;
  }
}
