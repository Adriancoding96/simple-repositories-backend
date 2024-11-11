package com.adrian.simple_repositories.model;

import java.time.LocalDateTime;
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
import jakarta.persistence.Table;

@Entity
@Table(name = "branches")
public class Branch {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String branchName;

  private LocalDateTime latestPushToBranch;

  @ManyToOne
  @JoinColumn(name = "repo_id")
  private Repo repo;

  @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
  private List<Push> pushes = new ArrayList<>();

  @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserRepoBranchActivity> activities = new ArrayList<>();

  public Branch() {
    
  }

  public Branch(Long id, String branchName, LocalDateTime latestPushToBranch, Repo repo) {
    this.id = id;
    this.branchName = branchName;
    this.latestPushToBranch = latestPushToBranch;
    this.repo = repo;
  }

  public Branch(Long id, String branchName, LocalDateTime latestPushToBranch, Repo repo, List<Push> pushes) {
    this.id = id;
    this.branchName = branchName;
    this.latestPushToBranch = latestPushToBranch;
    this.repo = repo;
    this.pushes = pushes;
  }

  public Long getId() {
    return id;
  }

  public String getBranchName() {
    return branchName;
  }

  public LocalDateTime getLatestPushToBranch() {
    return latestPushToBranch;
  }

  public Repo getRepo() {
    return repo;
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

  public void setLatestPushToBranch(LocalDateTime latestPushToBranch) {
    this.latestPushToBranch = latestPushToBranch;
  }

  public void setRepo(Repo repo) {
    this.repo = repo;
  }

  public void setPushes(List<Push> pushes) {
    this.pushes = pushes;
  }
}
