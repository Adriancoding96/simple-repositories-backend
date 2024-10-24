package com.adrian.simple_repositories.dto.project;

import java.util.List;

import com.adrian.simple_repositories.dto.folder.FolderFullDTO;

public class ProjectFullDTO {

  private String projectName;
  private String projectInformation;
  private List<FolderFullDTO> folders;

  public ProjectFullDTO() {

  }

  public ProjectFullDTO(String projectName, String projectInformation, List<FolderFullDTO> folders) {
    this.projectName = projectName;
    this.projectInformation = projectInformation;
    this.folders = folders;
  }

  public String getProjectName() {
    return projectName;
  }

  public String getProjectInformation() {
    return projectInformation;
  }

  public List<FolderFullDTO> getFolders() {
    return folders;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public void setProjectInformation(String projectInformation) {
    this.projectInformation = projectInformation;
  }

  public void setFolders(List<FolderFullDTO> folders) {
    this.folders = folders;
  }

}
