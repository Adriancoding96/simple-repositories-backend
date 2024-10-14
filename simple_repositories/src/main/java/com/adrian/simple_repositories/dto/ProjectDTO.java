package com.adrian.simple_repositories.dto;
 
import java.util.List;

public class ProjectDTO {
  
  private Long id;
  private String projectName;
  private String projectInformation;
  private List<Long> folders;

  public ProjectDTO() {

  }

  public ProjectDTO(Long id, String projectName, String projectInformation, List<Long> folders) {
    this.id = id;
    this.projectName = projectName;
    this.projectInformation = projectInformation;
    this.folders = folders;
  }

  public Long getId() {
    return this.id;
  }

  public String getProjectName() {
    return this.projectName;
  }

  public String getProjectInformation() {
    return projectInformation;
  }

  public List<Long> getFolders() {
    return this.folders;
  }

  public void setId(Long id) {
    this.id = id;
  } 

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public void setProjectInformation(String projectInformation) {
    this.projectInformation = projectInformation;
  }

  public void setFolders(List<Long> folders) {
    this.folders = folders;
  }
}
