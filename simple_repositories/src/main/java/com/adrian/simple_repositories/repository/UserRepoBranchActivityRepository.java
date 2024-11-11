package com.adrian.simple_repositories.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrian.simple_repositories.model.UserRepoBranchActivity;

public interface UserRepoBranchActivityRepository extends JpaRepository<UserRepoBranchActivity, Long> {

}
