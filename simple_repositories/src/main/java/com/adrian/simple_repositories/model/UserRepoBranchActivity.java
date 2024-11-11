package com.adrian.simple_repositories.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_repo_branch_activities")
public class UserRepoBranchActivity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime dateTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "repo_id")
  private Repo repo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "branch_id")
  private Branch branch;

  public UserRepoBranchActivity() {

  }

  public UserRepoBranchActivity(Long id, LocalDateTime dateTime, User user, Repo repo, Branch branch) {
    this.id = id;
    this.dateTime = dateTime;
    this.user = user;
    this.repo = repo;
    this.branch = branch;
  }

  public Long getId() {
    return id;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public User getUser() {
    return user;
  }

  public Repo getRepo() {
    return repo;
  }
  
  public Branch getBranch() {
    return branch;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setRepo(Repo repo) {
    this.repo = repo;
  }

  public void setBranch(Branch branch) {
    this.branch = branch;
  }
}
