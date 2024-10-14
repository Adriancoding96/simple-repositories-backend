package com.adrian.simple_repositories.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrian.simple_repositories.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {

}
