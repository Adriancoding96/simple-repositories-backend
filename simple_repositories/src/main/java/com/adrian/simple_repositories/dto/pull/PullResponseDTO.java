package com.adrian.simple_repositories.dto.pull;

import com.adrian.simple_repositories.dto.repo.RepoFullDTO;
/*
 * PullResponseDTO returns the repo information and the latest commit hash
 * to the repo. The commit hash is updated on the local machine, to later
 * be used to verify the user is working on the latest version of the repo
 */
public class PullResponseDTO {

  private boolean success;

  private String message;

  private String commitUuid;
  
  private RepoFullDTO repoDTO;

  public PullResponseDTO() {

  }

  public PullResponseDTO(boolean success, String message, String commitUuid, RepoFullDTO repoDTO) {
    this.success = success;
    this.message = message;
    this.commitUuid = commitUuid;
    this.repoDTO = repoDTO;
  }

  public String getCommitHash() {
    return commitUuid;
  }

  public RepoFullDTO getRepoDTO() {
    return repoDTO;
  }

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setCommitHash(String commitUuid) {
    this.commitUuid = commitUuid;
  }

  public void setRepoDTO(RepoFullDTO repoDTO) {
    this.repoDTO = repoDTO;
  }

}
