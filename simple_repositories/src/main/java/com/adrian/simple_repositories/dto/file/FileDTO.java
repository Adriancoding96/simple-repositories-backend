package com.adrian.simple_repositories.dto.file;

public class FileDTO {

  private String fileName;
  private String extension;
  private String path;
  private byte[] content;
  private Long folderId;
  private String uuid;

  public FileDTO() {

  } 

  public FileDTO(String fileName, String extension, String path, byte[] content, Long folderId, String uuid) {
    this.fileName = fileName;
    this.extension = extension;
    this.path = path;
    this.content = content;
    this.folderId = folderId;
    this.uuid = uuid;
  }

  public String getFileName() {
    return this.fileName;
  }

  public String getExtension() {
    return this.extension;
  }

  public String getPath() {
    return path;
  }

  public byte[] getContent() {
    return this.content;
  }

  public Long getFolderId() {
    return this.folderId;
  }

  public String getUuid() {
    return uuid;
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

  public void setContent(byte[] content) {
    this.content = content;
  }

  public void setFolderId(Long folderId) {
    this.folderId = folderId;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

}
