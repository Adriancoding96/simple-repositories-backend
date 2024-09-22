package com.adrian.simple_repositories.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Project {
  
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String projectName;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Folder> folders = new ArrayList<>();

  public Project() {

  }

  public Project(Long id, String projectName, List<File> files, List<Folder> folders) {
    this.id = id;
    this.projectName = projectName;
    this.folders = folders;
  }

  public Long getId() {
    return this.id;
  }

  public String getProjectName() {
    return this.projectName;
  }

  public List<Folder> getFolders() {
    return this.folders;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public void setFolders(List<Folder> folders) {
    this.folders = folders;
  }

  @Override
  public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Project{id=").append(id)
        .append(", projectName='").append(projectName).append('\'');

      if (folders != null && !folders.isEmpty()) {
          sb.append(", folders=[");
          for (Folder folder : folders) {
              sb.append(folder.getFolderName()).append(", ");
          }
          sb.setLength(sb.length() - 2); 
          sb.append(']');
      } else {
          sb.append(", folders=[]");
      }

      sb.append('}');
      return sb.toString();
  }

}
