package com.adrian.simple_repositories.dto;


public class UniqueIdentifierDTO{

  private String type;
  private String uuid;

  public UniqueIdentifierDTO(String type, String uuid) {
    this.type = type;
    this.uuid = uuid;
  }

  public String getType() {
    return type;
  }

  public String getUuid() {
    return uuid;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setId(String uuid) {
    this.uuid = uuid;
  }

}
