package com.adrian.simple_repositories.model;

import java.time.LocalDateTime;
import java.util.UUID;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "pushes")
//@ValidPush
public class Push {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, updatable = false)
  private String uuid;

  private String commitHash;

  private String commitMessage;

  private LocalDateTime dateTime;

  @ManyToOne
  @JoinColumn(name = "branch_id")
  private Branch branch;
  
  @ManyToOne
  @JoinColumn(name = "repo_id")
  private Repo repo;

  @ManyToOne
  @JoinColumn(name = "directory_id")
  private Directory directory;

  @ManyToOne
  @JoinColumn(name = "file_id")
  private File file;

  @PrePersist
  private void generateUuid() {
    if(uuid != null) return;
    uuid = UUID.randomUUID().toString();
  }

  public Push() {

  }

  public Push(Long id, String commitHash, String commitMessage, LocalDateTime dateTime, Branch branch, Repo repo) {
    this.id = id;
    this.commitHash = commitHash;
    this.commitMessage = commitMessage;
    this.dateTime = dateTime;
    this.branch = branch;
    this.repo = repo;
  }  

  public Push(Long id, String commitHash, String commitMessage, LocalDateTime dateTime, Branch branch, Directory directory) {
    this.id = id;
    this.commitHash = commitHash;
    this.commitMessage = commitMessage;
    this.dateTime = dateTime;
    this.branch = branch;
    this.directory = directory;
  }

  public Push(Long id, String commitHash, String commitMessage, LocalDateTime dateTime, Branch branch, File file) {
    this.id = id;
    this.commitHash = commitHash;
    this.commitMessage = commitMessage;
    this.dateTime = dateTime; 
    this.branch = branch;
    this.file = file;
  }

  public Long getId() {
    return id;
  }

  public String getUuid() {
    return uuid;
  }

  public String getCommitHash() {
    return commitHash;
  }

  public String getCommitMessage() {
    return commitMessage;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public Branch getBranch() {
    return branch;
  }

  public Repo getRepo() {
    return repo;
  }

  public Directory getDirectory() {
    return directory;
  }

  public File getFile() {
    return file;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public void setCommitHash(String commitHash) {
    this.commitHash = commitHash;
  }

  public void setCommitMessage(String commitMessage) {
    this.commitMessage = commitMessage;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public void setBranch(Branch branch) {
    this.branch = branch;
  }

  public void setRepo(Repo repo) {
    this.repo = repo;
  }

  public void setDirectory(Directory directory) {
    this.directory = directory;
  }

  public void setFile(File file) {
    this.file = file;
  }

  @Override
  public String toString() {
    return "Push{" +
            "id=" + id +
            ", commitHash='" + commitHash + '\'' +
            ", commitMessage='" + commitMessage + '\'' +
            ", branchId=" + (branch != null ? branch.getId() : null) +
            ", repo=" + (repo != null ? repo.toStringWithoutPush() : null) +
            ", directory=" + (directory != null ? directory.toStringWithoutPush() : null) +
            ", file=" + (file != null ? file.toStringWithoutPush() : null) +
            '}';
  }


}
