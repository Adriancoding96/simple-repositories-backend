package com.adrian.simple_repositories.dto.push;

import com.adrian.simple_repositories.dto.Content;

public class PushRequestDTO {

  private String repoUuid;
  private String commitMessage;
  private String branchName;
  private Content content;

  public PushRequestDTO() {

  }

  public PushRequestDTO(String repoUuid, String commitMessage, String branchName, Content content) {
    this.repoUuid = repoUuid;
    this.commitMessage = commitMessage;
    this.branchName = branchName;
    this.content = content;
  }

  public String getRepoUuid() {
    return repoUuid;
  }

  public String getCommitMessage() {
    return commitMessage;
  }

  public String getBranchName() {
    return branchName;
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

  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }

  public void setContent(Content content) {
    this.content = content;
  }
}
