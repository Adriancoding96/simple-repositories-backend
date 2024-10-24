package com.adrian.simple_repositories.dto.project;

public class ProjectIdentifierRequestDTO {

  private String projectName;

  private String email;

  public ProjectIdentifierRequestDTO() {

  }
    
  public ProjectIdentifierRequestDTO(String projectName, String email) {
    this.projectName = projectName;
    this.email = email;
  }

  public String getProjectName() {
    return projectName;
  }

  public String getEmail() {
    return email;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
