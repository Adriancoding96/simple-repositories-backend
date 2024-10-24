package com.adrian.simple_repositories.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.adrian.simple_repositories.model.Push;

public interface PushRepository extends JpaRepository<Push, Long> {

  //@EntityGraph(value = "Push.full", type = EntityGraph.EntityGraphType.LOAD)
  //Optional<Push> findById(Long id);
  
  Optional<Push> findFirstByBranchBranchNameOrderByDateTimeDesc(String branchName);

}
