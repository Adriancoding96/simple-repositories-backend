package com.adrian.simple_repositories.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Folder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String folderName;

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

  public Folder() {

  }

  public Folder(Long id, String folderName, List<File> files, 
                  List<Folder> folders, Project project, Folder parentFolder) {
    this.id = id;
    this.folderName = folderName;
    this.files = files;
    this.folders = folders;
    this.project = project;
    this.parentFolder = parentFolder;
  }

  public Long getId() {
    return this.id;
  }

  public String getFolderName() {
    return this.folderName;
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

  public void setFolderName(String folderName) {
    this.folderName = folderName;
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

}
