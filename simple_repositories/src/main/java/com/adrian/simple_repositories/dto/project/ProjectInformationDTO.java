package com.adrian.simple_repositories.dto.project;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ProjectInformationDTO {

  private Long id;

  private String projectName;

  private String projectInformation;

  private Map<String, Integer> fileTypes = new HashMap<>();
 
  public ProjectInformationDTO() {

  }

  public ProjectInformationDTO(Long id, String projectName, String projectInformation, Map<String, Integer> fileTypes) {
    this.id = id;
    this.projectName = projectName;
    this.projectInformation = projectInformation;
    this.fileTypes = fileTypes;
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

  public Map<String, Integer> getFileTypes() {
    return fileTypes;
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

  public void setFileTypes(Map<String, Integer> fileTypes) {
    this.fileTypes = fileTypes;
  }

  public void addAllFileTypes(List<String> types) {
    for(String type : types) {
      addFileType(type);
    }
  }

  public void addFileType(String fileType) {
    if(fileTypes.containsKey(fileType)) {
      fileTypes.put(fileType, fileTypes.get(fileType) + 1);
    } else {
      fileTypes.put(fileType, 1);
    }
  }
}
