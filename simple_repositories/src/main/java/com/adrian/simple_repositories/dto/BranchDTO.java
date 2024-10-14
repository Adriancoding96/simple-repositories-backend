package com.adrian.simple_repositories.dto;

public class BranchDTO {

  private Long id;
  private String branchName;
  private Long projectId;

  public BranchDTO() {

  }

  public BranchDTO(Long id, String branchName, Long projectId) {
    this.id = id;
    this.branchName = branchName;
    this.projectId = projectId;
  }

  public Long getId() {
    return id;
  }

  public String getBranchName() {
    return branchName;
  }

  public Long getProjectId() {
    return projectId;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }

  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }
}
