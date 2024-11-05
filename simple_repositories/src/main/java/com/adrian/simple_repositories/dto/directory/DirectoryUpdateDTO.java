package com.adrian.simple_repositories.dto.directory;

public class DirectoryUpdateDTO {
  
  private String directoryName;

  private String filePath;

  public DirectoryUpdateDTO() {

  }

  public DirectoryUpdateDTO(String directoryName, String filePath) {
    this.directoryName = directoryName;
    this.filePath = filePath;
  }

  public String getDirectoryName() {
    return directoryName;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setDirectoryName(String directoryName) {
    this.directoryName = directoryName;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

}
