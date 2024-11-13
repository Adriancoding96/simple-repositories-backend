package com.adrian.simple_repositories.dto.file;

import com.adrian.simple_repositories.dto.Content;

public class FileDTO implements Content {

  private String fileName;
  private String extension;
  private String path;
  private byte[] content;
  private String directoryUuid;
  private String uuid;

  public FileDTO() {

  } 

  public FileDTO(String fileName, String extension, String path, byte[] content, String directoryUuid, String uuid) {
    this.fileName = fileName;
    this.extension = extension;
    this.path = path;
    this.content = content;
    this.directoryUuid = directoryUuid;
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

  public String getDirectoryUuid() {
    return this.directoryUuid;
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

  public void setDirectoryUuid(String directoryUuid) {
    this.directoryUuid = directoryUuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

}
