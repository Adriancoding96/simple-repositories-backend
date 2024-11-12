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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Entity
@Table(name = "repos")
public class Repo {
  
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, updatable = false)
  private String uuid;

  private String repoName;

  private String repoInformation;

  @OneToMany(mappedBy = "repo", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Directory> directories = new ArrayList<>();

  @OneToMany(mappedBy = "repo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
  private List<Push> pushes = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "repo", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Branch> branches = new ArrayList<>();

  @OneToMany(mappedBy = "repo", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserRepoBranchActivity> activities = new ArrayList<>();


  /*
   * Regenerates uuid everytime repo gets updated. this is to be able to check if your
   * version on the local machine is the same as in the database
   */
  @PrePersist
  @PreUpdate
  public void generateUuid() {
    if(uuid != null) return;
    uuid = UUID.randomUUID().toString();
  }

  public Repo() {

  }

  public Repo(Long id, String repoName, String repoInformation, List<File> files, List<Directory> directories, User user) {
    this.id = id;
    this.repoName = repoName;
    this.repoInformation = repoInformation;
    this.directories = directories;
    this.user = user;
  }

  public Long getId() {
    return this.id;
  }

  public String getUuid() {
    return uuid;
  }

  public String getRepoName() {
    return this.repoName;
  }

  public String getRepoInformation() {
    return repoInformation;
  }

  public List<Directory> getDirectories() {
    return this.directories;
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

  public void setRepoName(String repoName) {
    this.repoName = repoName;
  }

  public void setRepoInformation(String repoInformation) {
    this.repoInformation = repoInformation;
  }

  public void setDirectories(List<Directory> directories) {
    this.directories = directories;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String toStringWithoutPush() {
    return "Repo {" +
            "id=" + id +
            ", repoName='" + repoName + '\'' +
            ", repoInformation='" + repoInformation + '\'' +
            ", directories=" + directories.stream()
                                    .map(Directory::toStringWithoutRepo)
                                    .collect(Collectors.toList()) +
            '}';
  }
}
