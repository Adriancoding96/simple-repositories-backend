package com.adrian.simple_repositories.dto.folder;

import java.util.List;

import com.adrian.simple_repositories.dto.file.FileDTO;

public class FolderFullDTO {

  private String folderName;
  private String path;
  private List<FileDTO> files;
  private List<FolderFullDTO> folders;
  private Long projectId;
  private Long parentFolderId;

  public FolderFullDTO() {

  } 

  public FolderFullDTO(String folderName, String path, List<FileDTO> files, List<FolderFullDTO> folders, Long projectId, Long parentFolderId) {
    this.folderName = folderName;
    this.path = path;
    this.files = files;
    this.folders = folders;
    this.projectId = projectId;
    this.parentFolderId= parentFolderId;
  }

  public String getFolderName() {
    return folderName;
  }

  public String getPath() {
    return path;
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

  public void setFolderName(String folderName) {
    this.folderName = folderName;
  }

  public void setPath(String path) {
    this.path = path;
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
