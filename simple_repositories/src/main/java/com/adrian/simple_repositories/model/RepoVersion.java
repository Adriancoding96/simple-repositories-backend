package com.adrian.simple_repositories.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/*
 * RepoVersion is used to store the uuids of old versions of a repository,
 * this is used for when a end user requests a repo by a old uuid it returns
 * this RepoVersion entity containing the database identifier of the repo
 * to be used for fetch instead.
 */
@Entity
@Table(name = "repo_versions")
public class RepoVersion {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long repoId;

  @Column(nullable = false)
  private String oldUuid;

  @Column(nullable = false)
  private LocalDateTime timestamp;

  public RepoVersion() {
    
  }

  public RepoVersion(Long repoId, String oldUuid) {
    this.repoId = repoId;
    this.oldUuid = oldUuid;
    this.timestamp = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public Long getRepoId() {
    return repoId;
  }

  public String getOldUuid() {
    return oldUuid;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setRepoId(Long repoId) {
    this.repoId = repoId;
  }

  public void setOldUuid(String oldUuid) {
    this.oldUuid = oldUuid;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }
}
