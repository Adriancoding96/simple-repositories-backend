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
@Table(name = "user_project_activities")
public class UserProjectActivity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime dateTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id")
  private Project project;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "branch_id")
  private Branch branch;

  public UserProjectActivity() {

  }

  public UserProjectActivity(Long id, LocalDateTime dateTime, User user, Project project, Branch branch) {
    this.id = id;
    this.dateTime = dateTime;
    this.user = user;
    this.project = project;
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

  public Project getProject() {
    return project;
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

  public void setProject(Project project) {
    this.project = project;
  }

  public void setBranch(Branch branch) {
    this.branch = branch;
  }
}
