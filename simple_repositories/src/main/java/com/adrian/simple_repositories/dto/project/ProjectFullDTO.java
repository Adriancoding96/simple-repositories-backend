package com.adrian.simple_repositories.dto.project;

import java.util.List;

import com.adrian.simple_repositories.dto.directory.DirectoryFullDTO;

public class ProjectFullDTO {

  private String projectName;
  private String projectInformation;
  private List<DirectoryFullDTO> directories;

  public ProjectFullDTO() {

  }

  public ProjectFullDTO(String projectName, String projectInformation, List<DirectoryFullDTO> directories) {
    this.projectName = projectName;
    this.projectInformation = projectInformation;
    this.directories = directories;
  }

  public String getProjectName() {
    return projectName;
  }

  public String getProjectInformation() {
    return projectInformation;
  }

  public List<DirectoryFullDTO> getDirectories() {
    return directories;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public void setProjectInformation(String projectInformation) {
    this.projectInformation = projectInformation;
  }

  public void setDirectories(List<DirectoryFullDTO> directories) {
    this.directories = directories;
  }

}
