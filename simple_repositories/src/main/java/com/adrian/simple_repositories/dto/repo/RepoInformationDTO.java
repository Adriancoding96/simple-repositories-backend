package com.adrian.simple_repositories.dto.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepoInformationDTO {

  private Long id;

  private String repoName;

  private String repoInformation;

  private Map<String, Integer> fileTypes = new HashMap<>();
 
  public RepoInformationDTO() {

  }

  public RepoInformationDTO(Long id, String repoName, String repoInformation, Map<String, Integer> fileTypes) {
    this.id = id;
    this.repoName = repoName;
    this.repoInformation = repoInformation;
    this.fileTypes = fileTypes;
  }

  public Long getId() {
    return id;
  }

  public String getRepoName() {
    return repoName;
  }

  public String getRepoInformation() {
    return repoInformation;
  }

  public Map<String, Integer> getFileTypes() {
    return fileTypes;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setRepoName(String repoName) {
    this.repoName = repoName;
  }

  public void setRepoInformation(String repoInformation) {
    this.repoInformation = repoInformation;
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
