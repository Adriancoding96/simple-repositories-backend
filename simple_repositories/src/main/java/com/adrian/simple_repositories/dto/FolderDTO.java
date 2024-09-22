package com.adrian.simple_repositories.dto;

import java.util.List;

public class FolderDTO {
  
  private Long id;
  private String folderName;
  private List<Long> files;
  private List<Long> folders;
  private Long project;
  private Long parentFolder;

  public FolderDTO() {

  }

  public FolderDTO(Long id, String folderName, List<Long> files, List<Long> folders, Long project, long parentFolder) {
    this.id = id;
    this.folderName = folderName;
    this.files = files;
    this.folders = folders;
    this.project = project;
    this.parentFolder = parentFolder;
  }

  public Long getId() {
    return this.id;
  }

  public String getFolderName() {
    return this.folderName;
  }

  public List<Long> getFiles() {
    return this.files;
  }

  public List<Long> getFolders() {
    return this.folders;
  }

  public Long getProject() {
    return this.project;
  }

  public Long getParentFolder() {
    return this.parentFolder;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setFolderName(String folderName) {
    this.folderName = folderName;
  }

  public void setFiles(List<Long> files) {
    this.files = files;
  }

  public void setFolders(List<Long> folders) {
    this.folders = folders;
  }

  public void setProject(Long project) {
    this.project = project;
  }

  public void setParentFolder(Long parentFolder) {
    this.parentFolder = parentFolder;
  }

}

