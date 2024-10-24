package com.adrian.simple_repositories.dto.project;

public class ProjectUpdateDTO {

  String projectName;

  String projectInformation;

  public ProjectUpdateDTO() {

  }

  public ProjectUpdateDTO(String projectName, String projectInformation) {
    this.projectName = projectName;
    this.projectInformation = projectInformation;
  }

  public String getProjectName() {
    return projectName;
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
