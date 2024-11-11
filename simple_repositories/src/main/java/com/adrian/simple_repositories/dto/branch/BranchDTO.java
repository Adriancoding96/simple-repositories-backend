package com.adrian.simple_repositories.dto.branch;

import java.time.LocalDateTime;
import java.util.UUID;

public class BranchDTO {

  private Long id;
  private String branchName;
  private LocalDateTime latestPushToBranch;
  private String repoUuid;

  public BranchDTO() {

  }

  public BranchDTO(Long id, String branchName, LocalDateTime latestPushToBranch, String repoUuid) {
    this.id = id;
    this.branchName = branchName;
    this.latestPushToBranch = latestPushToBranch;
    this.repoUuid = repoUuid;
  }

  public Long getId() {
    return id;
  }

  public String getBranchName() {
    return branchName;
  }

  public LocalDateTime getLatestPushToBranch() {
    return latestPushToBranch;
  }

  public String getRepoUuid() {
    return repoUuid;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }

  public void setLatestPushToBranch(LocalDateTime latestPushToBranch) {
    this.latestPushToBranch = latestPushToBranch;
  }

  public void setRepoUuid(String repoUuid) {
    this.repoUuid = repoUuid;
  }
}
