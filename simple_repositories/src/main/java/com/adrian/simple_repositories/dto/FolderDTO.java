package com.adrian.simple_repositories.dto;

public class FolderDTO {
  
  private Long id;
  private String folderName;
  private Long projectId;
  private Long parentFolderId;

  public FolderDTO() {

  }

  public FolderDTO(Long id, String folderName, Long projectId, long parentFolderId) {
    this.id = id;
    this.folderName = folderName;
    this.projectId = projectId;
    this.parentFolderId = parentFolderId;
  }

  public Long getId() {
    return this.id;
  }

  public String getFolderName() {
    return this.folderName;
  }

  public Long getProjectId() {
    return this.projectId;
  }

  public Long getParentFolderId() {
    return this.parentFolderId;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setFolderName(String folderName) {
    this.folderName = folderName;
  }

  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }

  public void setParentFolderId(Long parentFolderId) {
    this.parentFolderId = parentFolderId;
  }

}

