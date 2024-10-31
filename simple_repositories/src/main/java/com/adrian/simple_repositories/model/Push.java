package com.adrian.simple_repositories.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.adrian.simple_repositories.annotation.ValidPush;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/*@NamedEntityGraph(
    name = "Push.full",
    attributeNodes = {
        @NamedAttributeNode(value = "project", subgraph = "projectSubgraph")
    },
    subgraphs = {
        @NamedSubgraph(
            name = "projectSubgraph",
            attributeNodes = {
                @NamedAttributeNode(value = "folders", subgraph = "folderSubgraph")
            }
        ),
        @NamedSubgraph(
            name = "folderSubgraph",
            attributeNodes = {
                @NamedAttributeNode(value = "folders", subgraph = "subFolderSubgraph"),
                @NamedAttributeNode(value = "files")
            }
        ),
        @NamedSubgraph(
            name = "subFolderSubgraph",
            attributeNodes = {
                @NamedAttributeNode(value = "folders"),
                @NamedAttributeNode(value = "files")
            }
        )
    }
)*/
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
  @JoinColumn(name = "project_id")
  private Project project;

  @ManyToOne
  @JoinColumn(name = "folder_id")
  private Folder folder;

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

  public Push(Long id, String commitHash, String commitMessage, LocalDateTime dateTime, Branch branch, Project project) {
    this.id = id;
    this.commitHash = commitHash;
    this.commitMessage = commitMessage;
    this.dateTime = dateTime;
    this.branch = branch;
    this.project = project;
  }  

  public Push(Long id, String commitHash, String commitMessage, LocalDateTime dateTime, Branch branch, Folder folder) {
    this.id = id;
    this.commitHash = commitHash;
    this.commitMessage = commitMessage;
    this.dateTime = dateTime;
    this.branch = branch;
    this.folder = folder;
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
    return "Push{" +
            "id=" + id +
            ", commitHash='" + commitHash + '\'' +
            ", commitMessage='" + commitMessage + '\'' +
            ", branchId=" + (branch != null ? branch.getId() : null) +
            ", project=" + (project != null ? project.toStringWithoutPush() : null) +
            ", folder=" + (folder != null ? folder.toStringWithoutPush() : null) +
            ", file=" + (file != null ? file.toStringWithoutPush() : null) +
            '}';
  }


}
