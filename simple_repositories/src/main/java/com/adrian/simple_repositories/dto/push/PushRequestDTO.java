package com.adrian.simple_repositories.dto.push;

import com.adrian.simple_repositories.dto.branch.BranchDTO;
import com.adrian.simple_repositories.dto.Content;

public class PushRequestDTO {

  private String commitMessage;
  private BranchDTO branchDTO;
  private Content content;

  public PushRequestDTO() {

  }

  public PushRequestDTO(String commitMessage, BranchDTO branchDTO, Content content) {
    this.commitMessage = commitMessage;
    this.branchDTO = branchDTO;
    this.content = content;
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
