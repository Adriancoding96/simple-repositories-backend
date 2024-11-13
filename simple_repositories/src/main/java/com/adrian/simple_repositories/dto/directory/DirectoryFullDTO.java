package com.adrian.simple_repositories.dto.directory;

import java.util.List;

import com.adrian.simple_repositories.dto.Content;
import com.adrian.simple_repositories.dto.file.FileDTO;

public class DirectoryFullDTO implements Content{

  private String directoryName;
  private String path;
  private List<FileDTO> files;
  private List<DirectoryFullDTO> directories;
  private String repoUuid;
  private String parentDirectoryUuid;

  public DirectoryFullDTO() {

  } 

  public DirectoryFullDTO(String directoryName, String path, List<FileDTO> files, List<DirectoryFullDTO> directories, String repoUuid, String parentDirectoryUuid) {
    this.directoryName = directoryName;
    this.path = path;
    this.files = files;
    this.directories = directories;
    this.repoUuid = repoUuid;
    this.parentDirectoryUuid = parentDirectoryUuid;
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

  public String getRepoUuid() {
    return repoUuid;
  }

  public String getParentDirectoryUuid() {
    return parentDirectoryUuid;
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

  public void setRepoUuid(String repoUuid) {
    this.repoUuid = repoUuid;
  }

  public void setParentDirectoryUuid(String parentDirectoryUuid) {
    this.parentDirectoryUuid = parentDirectoryUuid;
  }
}
