package com.adrian.simple_repositories.dto.folder;

public class FolderUpdateDTO {
  
  private String folderName;

  private String filePath;

  public FolderUpdateDTO() {

  }

  public FolderUpdateDTO(String folderName, String filePath) {
    this.folderName = folderName;
    this.filePath = filePath;
  }

  public String getFolderName() {
    return folderName;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFolderName(String folderName) {
    this.folderName = folderName;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

}
