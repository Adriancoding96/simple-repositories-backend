package com.adrian.simple_repositories.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrian.simple_repositories.model.RepoVersion;

public interface RepoVersionRepository extends JpaRepository<RepoVersion, Long> {

  boolean existsByOldUuid(String uuid);
  Optional<RepoVersion> findByOldUuid(String uuid);
}
