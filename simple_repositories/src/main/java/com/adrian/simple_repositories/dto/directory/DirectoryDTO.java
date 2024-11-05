package com.adrian.simple_repositories.dto.directory;

public class DirectoryDTO {
  
  private Long id;
  private String directoryUuid;
  private String directoryName;
  private String parentDirectoryUuid;

  public DirectoryDTO() {

  }

  public DirectoryDTO(Long id, String directoryUuid, String directoryName, String parentDirectoryUuid) {
    this.id = id;
    this.directoryUuid = directoryUuid;
    this.directoryName = directoryName;
    this.parentDirectoryUuid = parentDirectoryUuid;
  }

  public Long getId() {
    return this.id;
  }

  public String getDirectoryUuid() {
    return directoryUuid;
  }

  public String getDirectoryName() {
    return this.directoryName;
  }

  public String getParentDirectoryUuid() {
    return parentDirectoryUuid;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setDirectoryUuid(String directoryUuid) {
    this.directoryUuid = directoryUuid;
  }

  public void setDirectoryName(String directoryName) {
    this.directoryName = directoryName;
  }

  public void setParentDirectoryUuid(String parentDirectoryUuid) {
    this.parentDirectoryUuid = parentDirectoryUuid;
  }
}

