package com.adrian.simple_repositories.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adrian.simple_repositories.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {

  @Query("SELECT b FROM Branch b WHERE b.branchName =:branchName AND b.repo.uuid = :repoUuid")
  Optional<Branch> findBranchByNameAndRepoUuid(@Param("branchName") String branchName, @Param("repoUuid") String repoUuid);
  
}
