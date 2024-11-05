package com.adrian.simple_repositories.dto.directory;

import java.util.List;

import com.adrian.simple_repositories.dto.file.FileDTO;

public class DirectoryFullDTO {

  private String directoryName;
  private String path;
  private List<FileDTO> files;
  private List<DirectoryFullDTO> directories;
  private Long projectId;
  private Long parentDirectoryId;

  public DirectoryFullDTO() {

  } 

  public DirectoryFullDTO(String directoryName, String path, List<FileDTO> files, List<DirectoryFullDTO> directories, Long projectId, Long parentDirectoryId) {
    this.directoryName = directoryName;
    this.path = path;
    this.files = files;
    this.directories = directories;
    this.projectId = projectId;
    this.parentDirectoryId= parentDirectoryId;
  }

  public String getDirectoryName() {
    return directoryName;
  }

  public String getPath() {
    return path;
  }

  public List<FileDTO> getFiles() {
    return files;
  }

  public List<DirectoryFullDTO> getDirectories() {
    return directories;
  }

  public Long getProjectId() {
    return projectId;
  }

  public Long getParentDirectoryId() {
    return parentDirectoryId;
  }

  public void setDirectoryName(String directoryName) {
    this.directoryName = directoryName;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setFiles(List<FileDTO> files) {
    this.files = files;
  }

  public void setDirectories(List<DirectoryFullDTO> directories) {
    this.directories = directories;
  }

  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }

  public void setParentDirectoryId(Long parentDirectoryId) {
    this.parentDirectoryId = parentDirectoryId;
  }
}
