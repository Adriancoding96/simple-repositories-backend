package com.adrian.simple_repositories.dto.pull;

import com.adrian.simple_repositories.dto.repo.RepoFullDTO;
/*
 * PullResponseDTO returns the repo information and the latest commit hash
 * to the repo. The commit hash is updated on the local machine, to later
 * be used to verify the user is working on the latest version of the repo
 */
public class PullResponseDTO {
  
  private String commitHash;
  
  private RepoFullDTO repoDTO;

  public PullResponseDTO() {

  }

  public PullResponseDTO(String commitHash, RepoFullDTO repoDTO) {
    this.commitHash = commitHash;
    this.repoDTO = repoDTO;
  }

  public String getCommitHash() {
    return commitHash;
  }

  public RepoFullDTO getRepoDTO() {
    return repoDTO;
  }

  public void setCommitHash(String commitHash) {
    this.commitHash = commitHash;
  }

  public void setRepoDTO(RepoFullDTO repoDTO) {
    this.repoDTO = repoDTO;
  }
}
