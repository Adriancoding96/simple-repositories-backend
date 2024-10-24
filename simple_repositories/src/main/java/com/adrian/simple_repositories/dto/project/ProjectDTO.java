package com.adrian.simple_repositories.dto.project;
 
import java.util.List;

public class ProjectDTO {
  
  private String projectName;
  private String projectInformation;

  public ProjectDTO() {

  }

  public ProjectDTO(String projectName, String projectInformation) {
    this.projectName = projectName;
    this.projectInformation = projectInformation;
  }

  public String getProjectName() {
    return this.projectName;
  }

  public String getProjectInformation() {
    return projectInformation;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public void setProjectInformation(String projectInformation) {
    this.projectInformation = projectInformation;
  }
}
