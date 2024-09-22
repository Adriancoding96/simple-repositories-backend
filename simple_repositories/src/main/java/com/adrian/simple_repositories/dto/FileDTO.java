package com.adrian.simple_repositories.dto;

public class FileDTO {
  
  private Long id;
  private String fileName;
  private String extension;
  private byte[] content;
  private Long folderId;

  public FileDTO() {

  } 

  public FileDTO(Long id, String fileName, String extension, byte[] content, Long folderId) {
    this.id = id;
    this.fileName = fileName;
    this.extension = extension;
    this.content = content;
    this.folderId = folderId;
  }

  public Long getId() {
    return this.id;
  }

  public String getFileName() {
    return this.fileName;
  }

  public String getExtension() {
    return this.extension;
  }

  public byte[] getContent() {
    return this.content;
  }

  public Long getFolderId() {
    return this.folderId;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  public void setFolderId(Long folderId) {
    this.folderId = folderId;
  }

}
