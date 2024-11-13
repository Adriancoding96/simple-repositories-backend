package com.adrian.simple_repositories.dto.push;

import com.adrian.simple_repositories.dto.branch.BranchDTO;
import com.adrian.simple_repositories.dto.file.FileDTO;
import com.adrian.simple_repositories.dto.Content;

public class PushRequestDTO {

  private String repoUuid;
  private String commitMessage;
  private BranchDTO branchDTO;
  private Content content;

  public PushRequestDTO() {

  }

  public PushRequestDTO(String repoUuid, String commitMessage, BranchDTO branchDTO, Content content) {
    this.repoUuid = repoUuid;
    this.commitMessage = commitMessage;
    this.branchDTO = branchDTO;
    this.content = content;
  }

  public String getRepoUuid() {
    return repoUuid;
  }

  public String getCommitMessage() {
    return commitMessage;
  }

  public BranchDTO getBranchDTO() {
    return branchDTO;
  }

  public Content getContent() {
    return content;
  }

  public void setRepoUuid(String repoUuid) {
    this.repoUuid = repoUuid;
  }

  public void setCommitMessage(String commitMessage) {
    this.commitMessage = commitMessage;
  }

  public void setBranchDTO(BranchDTO branchDTO) {
    this.branchDTO = branchDTO;
  }

  public void setContent(Content content) {
    this.content = content;
  }
}
