package com.adrian.simple_repositories.dto;


import java.util.List;


public class FolderFullDTO {

  private Long id;
  private String folderName;
  private List<FileDTO> files;
  private List<FolderFullDTO> folders;
  private Long projectId;
  private Long parentFolderId;

  public FolderFullDTO() {

  } 

  public FolderFullDTO(Long id, String folderName, List<FileDTO> files, List<FolderFullDTO> folders, Long projectId, Long parentFolderId) {
    this.id = id;
    this.folderName = folderName;
    this.files = files;
    this.folders = folders;
    this.projectId = projectId;
    this.parentFolderId= parentFolderId;
  }

  public Long getId() {
    return id;
  }

  public String getFolderName() {
    return folderName;
  }

  public List<FileDTO> getFiles() {
    return files;
  }

  public List<FolderFullDTO> getFolders() {
    return folders;
  }

  public Long getProjectId() {
    return projectId;
  }

  public Long getParentFolderId() {
    return parentFolderId;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setFolderName(String folderName) {
    this.folderName = folderName;
  }

  public void setFiles(List<FileDTO> files) {
    this.files = files;
  }

  public void setFolders(List<FolderFullDTO> folders) {
    this.folders = folders;
  }

  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }

  public void setParentFolderId(Long parentFolderId) {
    this.parentFolderId = parentFolderId;
  }
}
