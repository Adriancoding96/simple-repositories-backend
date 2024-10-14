package com.adrian.simple_repositories.model;

import com.adrian.simple_repositories.annotation.ValidPush;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@ValidPush
public class Push {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String commitHash;

  private String commitMessage;

  @ManyToOne
  @JoinColumn(name = "branch_id")
  private Branch branch;
  
  @ManyToOne
  @JoinColumn(name = "project_id")
  private Project project;

  @ManyToOne
  @JoinColumn(name = "folder_id")
  private Folder folder;

  @ManyToOne
  @JoinColumn(name = "file_id")
  private File file;

  public Push() {

  }

  public Push(Long id, String commitHash, String commitMessage, Branch branch, Project project) {
    this.id = id;
    this.commitHash = commitHash;
    this.commitMessage = commitMessage;
    this.branch = branch;
    this.project = project;
  }  

  public Push(Long id, String commitHash, String commitMessage, Branch branch, Folder folder) {
    this.id = id;
    this.commitHash = commitHash;
    this.commitMessage = commitMessage;
    this.branch = branch;
    this.folder = folder;
  }

  public Push(Long id, String commitHash, String commitMessage, Branch branch, File file) {
    this.id = id;
    this.commitHash = commitHash;
    this.commitMessage = commitMessage;
    this.branch = branch;
    this.file = file;
  }

  public Long getId() {
    return id;
  }

  public String getCommitHash() {
    return commitHash;
  }

  public String getCommitMessage() {
    return commitMessage;
  }

  public Branch getBranch() {
    return branch;
  }

  public Project getProject() {
    return project;
  }

  public Folder getFolder() {
    return folder;
  }

  public File getFile() {
    return file;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setCommitHash(String commitHash) {
    this.commitHash = commitHash;
  }

  public void setCommitMessage(String commitMessage) {
    this.commitMessage = commitMessage;
  }

  public void setBranch(Branch branch) {
    this.branch = branch;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setFolder(Folder folder) {
    this.folder = folder;
  }

  public void setFile(File file) {
    this.file = file;
  }

  @Override
  public String toString() {
    return "id: " + this.id
      + "\ncommitHash: " + this.commitHash
      + "\ncommitMessage: " + this.commitMessage;
  }

}
