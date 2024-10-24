package com.adrian.simple_repositories.dto.push;

import java.util.UUID;

public class PushResponseDTO {
  
  private boolean success;
  
  private String message;
  
  private Long projectId;
  
  private Long folderId;
  
  private Long fileId;

  public PushResponseDTO() {

  }

  public PushResponseDTO(boolean success, String message, Long projectId, Long folderId, Long fileId) {
    this.success = success;
    this.message = message;
    this.projectId = projectId;
    this.folderId = folderId;
    this.fileId = fileId;
  }

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  public Long getProjectId() {
    return projectId;
  }

  public Long getFolderId() {
    return folderId;
  }

  public Long getFileId() {
    return fileId;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }

  public void setFolderId(Long folderId) {
    this.folderId = folderId;
  }

  public void setFileId(Long fileId) {
    this.fileId = fileId;
  }
}
