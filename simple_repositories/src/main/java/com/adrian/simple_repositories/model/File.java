
package com.adrian.simple_repositories.model;

import java.util.UUID;

import com.adrian.simple_repositories.model.marker.Node;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "files")
public class File implements Node {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(unique = true, nullable = false, updatable = false)
  private String uuid;

  private String fileName;
  
  private String extension;
  
  private String path;
  
  @Lob
  private byte[] content;

  @ManyToOne
  @JoinColumn(name = "directory_id", nullable = false)
  private Directory directory;

  @PrePersist
  public void generateUuid() {
    if(uuid != null) return;
    uuid = UUID.randomUUID().toString();
  }

  public File() {
    
  }

  public File(Long id, String fileName, String extension, String path, byte[] content, Directory directory) {
    this.id = id;
    this.fileName = fileName;
    this.extension = extension;
    this.path = path;
    this.content = content;
    this.directory = directory;
  }

  public Long getId() {
    return this.id;
  }

  public String getUuid() {
    return uuid;
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

  public Directory getDirectory() {
    return this.directory;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
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

  public void setDirectory(Directory directory) {
    this.directory = directory;
  }

  public String toStringWithoutDirectory() {
    return "File{" +
            "id=" + id +
            ", fileName='" + fileName + '\'' +
            ", extension='" + extension + '\'' +
            ", content='" + (content != null ? new String(content) : null) + '\'' +
            '}';
    }

    public String toStringWithoutPush() {
        return toStringWithoutDirectory(); 
    }
}
