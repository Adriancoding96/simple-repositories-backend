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
import jakarta.persistence.Table;

@Entity
@Table(name = "directories")
public class Directory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, updatable = false)
  private String uuid;

  private String directoryName;

  private String path;

  @OneToMany(mappedBy = "directory", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<File> files = new ArrayList<>(); 

  @OneToMany(mappedBy = "parentDirectory", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Directory> directories = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "project_id")
  private Project project;

  @ManyToOne
  @JoinColumn(name = "parent_directory_id")
  private Directory parentDirectory;

  @PrePersist
  private void generateUuid() {
    if(uuid != null) return;
    uuid = UUID.randomUUID().toString();
  }

  public Directory() {

  }

  public Directory(Long id, String directoryName, String path, List<File> files, 
                  List<Directory> directories, Project project, Directory parentDirectory) {
    this.id = id;
    this.directoryName = directoryName;
    this.path = path;
    this.files = files;
    this.directories = directories;
    this.project = project;
    this.parentDirectory = parentDirectory;
  }

  public Long getId() {
    return this.id;
  }

  public String getUuid() {
    return uuid;
  }

  public String getDirectoryName() {
    return this.directoryName;
  }

  public String getPath() {
    return path;
  }

  public List<File> getFiles() {
    return this.files;
  }

  public List<Directory> getDirectories() {
    return this.directories;
  }

  public Project getProject() {
    return this.project;
  }

  public Directory getParentDirectory() {
    return this.parentDirectory;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public void setDirectoryName(String directoryName) {
    this.directoryName = directoryName;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setFiles(List<File> files) {
    this.files = files;
  }

  public void setDirectories(List<Directory> directories) {
    this.directories = directories;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setParentDirectory(Directory parentDirectory) {
    this.parentDirectory = parentDirectory;
  }

  public String toStringWithoutProject() {
    return "Directory{" +
            "id=" + id +
            ", directoryName='" + directoryName + '\'' +
            ", subdirectories=" + directories.stream()
                                .map(Directory::toStringWithoutProject)
                                .collect(Collectors.toList()) +
            ", files=" + files.stream()
                                .map(File::toStringWithoutDirectory)
                                .collect(Collectors.toList()) +
            '}';
  }

  public String toStringWithoutPush() {
    return "Directory{" +
            "id=" + id +
            ", directoryName='" + directoryName + '\'' +
            ", subdirectories=" + directories.stream()
                                .map(Directory::toStringWithoutPush)
                                .collect(Collectors.toList()) +
            ", files=" + files.stream()
                                .map(File::toStringWithoutDirectory)
                                .collect(Collectors.toList()) +
            '}';
  }

}
