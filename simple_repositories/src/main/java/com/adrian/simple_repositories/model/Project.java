package com.adrian.simple_repositories.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Entity
@Table(name = "projects")
public class Project {
  
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, updatable = false)
  private String uuid;

  private String projectName;

  private String projectInformation;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Folder> folders = new ArrayList<>();

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
  private List<Push> pushes = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Branch> branches = new ArrayList<>();

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserProjectActivity> activities = new ArrayList<>();

  @PrePersist
  public void generateUuid() {
    if(uuid != null) return;
    uuid = UUID.randomUUID().toString();
  }

  public Project() {

  }

  public Project(Long id, String projectName, String projectInformation, List<File> files, List<Folder> folders, User user) {
    this.id = id;
    this.projectName = projectName;
    this.projectInformation = projectInformation;
    this.folders = folders;
    this.user = user;
  }

  public Long getId() {
    return this.id;
  }

  public String getUuid() {
    return uuid;
  }

  public String getProjectName() {
    return this.projectName;
  }

  public String getProjectInformation() {
    return projectInformation;
  }

  public List<Folder> getFolders() {
    return this.folders;
  }

  public User getUser() {
    return user;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public void setProjectInformation(String projectInformation) {
    this.projectInformation = projectInformation;
  }

  public void setFolders(List<Folder> folders) {
    this.folders = folders;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String toStringWithoutPush() {
    return "Project{" +
            "id=" + id +
            ", projectName='" + projectName + '\'' +
            ", projectInformation='" + projectInformation + '\'' +
            ", folders=" + folders.stream()
                                    .map(Folder::toStringWithoutProject)
                                    .collect(Collectors.toList()) +
            '}';
  }
}
