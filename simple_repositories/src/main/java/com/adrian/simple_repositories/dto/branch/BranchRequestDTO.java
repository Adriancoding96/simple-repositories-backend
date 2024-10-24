package com.adrian.simple_repositories.dto.branch;

import java.util.UUID;

public class BranchRequestDTO {
  
  private String branchName;
  
  private UUID projectId;

  public BranchRequestDTO() {

  }

  public BranchRequestDTO(String branchName, UUID projectId) {
    this.branchName = branchName;
    this.projectId = projectId;
  }

  public String getBranchName() {
    return branchName;
  }

  public UUID getProjectId() {
    return projectId;
  }

  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }

  public void setProjectId(UUID projectId) {
    this.projectId = projectId;
  }
}
