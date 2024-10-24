package com.adrian.simple_repositories.dto.folder;

public class FolderDTO {
  
  private Long id;
  private String folderUuid;
  private String folderName;
  private String parentFolderUuid;

  public FolderDTO() {

  }

  public FolderDTO(Long id, String folderUuid, String folderName, String parentFolderUuid) {
    this.id = id;
    this.folderUuid = folderUuid;
    this.folderName = folderName;
    this.parentFolderUuid = parentFolderUuid;
  }

  public Long getId() {
    return this.id;
  }

  public String getFolderUuid() {
    return folderUuid;
  }

  public String getFolderName() {
    return this.folderName;
  }

  public String getParentFolderUuid() {
    return parentFolderUuid;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setFolderUuid(String folderUuid) {
    this.folderUuid = folderUuid;
  }

  public void setFolderName(String folderName) {
    this.folderName = folderName;
  }

  public void setParentFolderUuid(String parentFolderUuid) {
    this.parentFolderUuid = parentFolderUuid;
  }
}

