package com.adrian.simple_repositories.model;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Branch {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String branchName;

  @ManyToOne
  @JoinColumn(name = "project_id")
  private Project project;

  @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
  private List<Push> pushes = new ArrayList<>();

  @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserProjectActivity> activities = new ArrayList<>();

  public Branch() {
    
  }

  public Branch(Long id, String branchName, Project project) {
    this.id = id;
    this.branchName = branchName;
    this.project = project;
  }

  public Branch(Long id, String branchName, Project project, List<Push> pushes) {
    this.id = id;
    this.branchName = branchName;
    this.project = project;
    this.pushes = pushes;
  }

  public Long getId() {
    return id;
  }

  public String getBranchName() {
    return branchName;
  }

  public Project getProject() {
    return project;
  }

  public List<Push> getPushes() {
    return pushes;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setPushes(List<Push> pushes) {
    this.pushes = pushes;
  }
}
