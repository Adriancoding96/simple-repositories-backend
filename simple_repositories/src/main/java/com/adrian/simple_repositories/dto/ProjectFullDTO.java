package com.adrian.simple_repositories.dto;
import java.util.List;

public class ProjectFullDTO {

  private Long id;
  private String projectName;
  private String projectInformation;
  private List<FolderFullDTO> folders;

  public ProjectFullDTO() {

  }

  public ProjectFullDTO(Long id, String projectName, String projectInformation, List<FolderFullDTO> folders) {
    this.id = id;
    this.projectName = projectName;
    this.projectInformation = projectInformation;
    this.folders = folders;
  }

  public Long getId() {
    return id;
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

  public void setId(Long id) {
    this.id = id;
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
