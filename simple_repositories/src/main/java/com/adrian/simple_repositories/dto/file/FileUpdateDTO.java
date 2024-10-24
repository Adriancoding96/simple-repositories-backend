package com.adrian.simple_repositories.dto.file;

public class FileUpdateDTO {

  private String fileName;
  private String extension;
  private String path;

  public FileUpdateDTO() {

  }

  public FileUpdateDTO(String fileName, String extension, String path) {
    this.fileName = fileName;
    this.extension = extension;
    this.path = path;
  }

  public String getFileName() {
    return fileName;
  }

  public String getExtension() {
    return extension;
  }

  public String getPath() {
    return path;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public void setPath(String path) {
    this.path = path;
  }
}
