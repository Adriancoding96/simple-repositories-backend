
package com.adrian.simple_repositories.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class File{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String fileName;
  private String extension;
  
  @Lob
  private byte[] content;

  @ManyToOne
  @JoinColumn(name = "folder_id", nullable = false)
  private Folder folder;


  public File() {
    
  }

  public File(Long id, String fileName, String extension, byte[] content, Folder folder) {
    this.id = id;
    this.fileName = fileName;
    this.extension = extension;
    this.content = content;
    this.folder = folder;
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

  public Folder getFolder() {
    return this.folder;
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

  public void setFolder(Folder folder) {
    this.folder = folder;
  }
}
