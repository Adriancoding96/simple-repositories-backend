package com.adrian.simple_repositories.dto.push;

import com.adrian.simple_repositories.dto.branch.BranchDTO;
import com.adrian.simple_repositories.dto.project.ProjectFullDTO;
import com.adrian.simple_repositories.dto.folder.FolderFullDTO;
import com.adrian.simple_repositories.dto.file.FileDTO;

public class PushDTO {

  private Long id;
  private String commitHash;
  private String commitMessage;
  private String ownerEmail;
  private BranchDTO branchDTO;
  private ProjectFullDTO projectFullDTO;
  private FolderFullDTO folderFullDTO;
  private FileDTO fileDTO;
  
  public PushDTO() {

  }

  public PushDTO(Long id, String commitHash, String commitMessage, String ownerEmail, BranchDTO branchDTO,  ProjectFullDTO projectFullDTO) {
    this.id = id;
    this.commitHash = commitHash;
    this.commitMessage = commitMessage;
    this.ownerEmail = ownerEmail;
    this.branchDTO = branchDTO;
    this.projectFullDTO = projectFullDTO;
  }
  
  public PushDTO(Long id, String commitHash, String commitMessage, String ownerEmail, BranchDTO branchDTO, FolderFullDTO folderFullDTO) {
    this.id = id;
    this.commitHash = commitHash;
    this.commitMessage = commitMessage;
    this.ownerEmail = ownerEmail;
    this.branchDTO = branchDTO;
    this.folderFullDTO = folderFullDTO;
  }
  
  public PushDTO(Long id, String commitHash, String commitMessage, String ownerEmail, BranchDTO branchDTO, FileDTO fileDTO) {
    this.id = id;
    this.commitHash = commitHash;
    this.commitMessage = commitMessage;
    this.ownerEmail = ownerEmail;
    this.branchDTO = branchDTO;
    this.fileDTO = fileDTO;
  }

  public Long getId() {
    return id;
  }

  public String getCommitHash() {
    return commitHash;
  }

  public String getCommitMessage() {
    return commitMessage;
  }

  public String getOwnerEmail() {
    return ownerEmail;
  }

  public BranchDTO getBranchDTO() {
    return branchDTO;
  }

  public ProjectFullDTO getProjectFullDTO() {
    return projectFullDTO;
  }

  public FolderFullDTO getFolderFullDTO() {
    return folderFullDTO;
  }

  public FileDTO getFileDTO() {
    return fileDTO;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setCommitHash(String commitHash) {
    this.commitHash = commitHash;
  }

  public void setCommitMessage(String commitMessage) {
    this.commitMessage = commitMessage;
  }
  
  public void setOwnerEmail(String ownerEmail) {
    this.ownerEmail = ownerEmail;
  }

  public void setBranchDTO(BranchDTO branchDTO) {
    this.branchDTO = branchDTO;
  }

  public void setProjectDTO(ProjectFullDTO projectFullDTO) {
    this.projectFullDTO = projectFullDTO;
  }

  public void setFolderDTO(FolderFullDTO folderFullDTO) {
    this.folderFullDTO = folderFullDTO;
  }

  public void setFileDTO(FileDTO fileDTO) {
    this.fileDTO = fileDTO;
  }
}
