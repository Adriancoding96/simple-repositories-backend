package com.adrian.simple_repositories.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

@Entity
public class Folder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, updatable = false)
  private String uuid;

  private String folderName;

  private String path;

  @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<File> files = new ArrayList<>(); 

  @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Folder> folders = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "project_id")
  private Project project;

  @ManyToOne
  @JoinColumn(name = "parent_folder_id")
  private Folder parentFolder;

  @PrePersist
  private void generateUuid() {
    if(uuid != null) return;
    uuid = UUID.randomUUID().toString();
  }

  public Folder() {

  }

  public Folder(Long id, String folderName, String path, List<File> files, 
                  List<Folder> folders, Project project, Folder parentFolder) {
    this.id = id;
    this.folderName = folderName;
    this.path = path;
    this.files = files;
    this.folders = folders;
    this.project = project;
    this.parentFolder = parentFolder;
  }

  public Long getId() {
    return this.id;
  }

  public String getUuid() {
    return uuid;
  }

  public String getFolderName() {
    return this.folderName;
  }

  public String getPath() {
    return path;
  }

  public List<File> getFiles() {
    return this.files;
  }

  public List<Folder> getFolders() {
    return this.folders;
  }

  public Project getProject() {
    return this.project;
  }

  public Folder getParentFolder() {
    return this.parentFolder;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public void setFolderName(String folderName) {
    this.folderName = folderName;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setFiles(List<File> files) {
    this.files = files;
  }

  public void setFolders(List<Folder> folders) {
    this.folders = folders;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setParentFolder(Folder parentFolder) {
    this.parentFolder = parentFolder;
  }

  public String toStringWithoutProject() {
    return "Folder{" +
            "id=" + id +
            ", folderName='" + folderName + '\'' +
            ", subFolders=" + folders.stream()
                                .map(Folder::toStringWithoutProject)
                                .collect(Collectors.toList()) +
            ", files=" + files.stream()
                                .map(File::toStringWithoutFolder)
                                .collect(Collectors.toList()) +
            '}';
  }

  public String toStringWithoutPush() {
    return "Folder{" +
            "id=" + id +
            ", folderName='" + folderName + '\'' +
            ", subFolders=" + folders.stream()
                                .map(Folder::toStringWithoutPush)
                                .collect(Collectors.toList()) +
            ", files=" + files.stream()
                                .map(File::toStringWithoutFolder)
                                .collect(Collectors.toList()) +
            '}';
  }

}
